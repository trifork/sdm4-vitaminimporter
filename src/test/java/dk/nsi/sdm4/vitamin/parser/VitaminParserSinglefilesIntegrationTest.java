package dk.nsi.sdm4.vitamin.parser;

import dk.nsi.sdm4.core.persistence.recordpersister.RecordPersister;
import dk.nsi.sdm4.testutils.TestDbConfiguration;
import dk.nsi.sdm4.vitamin.config.VitaminimporterApplicationConfig;
import dk.nsi.sdm4.vitamin.exception.InvalidVitaminDatasetException;
import dk.nsi.sdm4.vitamin.recordspecs.VitaminGrunddataRecordSpecsTest;
import dk.nsi.sdm4.vitamin.recordspecs.VitaminRecordSpecs;
import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = {VitaminimporterApplicationConfig.class, TestDbConfiguration.class})
public class VitaminParserSinglefilesIntegrationTest
{
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private VitaminParser parser;

	@Autowired
	private RecordPersister persister;

	@Test
	public void persistsAllLines() throws Exception {
	    importFile("data/vitaminer/nat01.txt");

		assertEquals(86, jdbcTemplate.queryForInt("SELECT COUNT(*) FROM VitaminGrunddata"));
	}

	@Test
	public void persistsNameWithNonAsciiCharacters() throws Exception {
		importFile("data/vitaminer/nat01.txt");

		assertEquals("Drogen`s Stærk Baldrian citrm.", jdbcTemplate.queryForObject("SELECT Navn FROM VitaminGrunddata where DrugID=52610061197", String.class));
		assertEquals("Ægte Venustorn", jdbcTemplate.queryForObject("SELECT Navn FROM VitaminGrunddata where DrugID=52610011893", String.class));
		assertEquals("orale dråber, opløsn", jdbcTemplate.queryForObject("SELECT FormTekst FROM VitaminGrunddata where DrugID=52610016293", String.class));
	}

	@Test
	public void persistsKarantaeneData() throws Exception {
		importFile("data/vitaminer/nat01.txt");

		assertEquals("20061009", jdbcTemplate.queryForObject("SELECT KarantaeneDato FROM VitaminGrunddata where DrugID=52610057196", String.class));
	}

	@Test
	public void makesNoExtraRowsWhenImportingSameDataTwice() throws Exception {
		importFile("data/historik/nat01-1.txt");
		assertEquals(1, jdbcTemplate.queryForInt("SELECT COUNT(*) FROM VitaminGrunddata"));

		importFile("data/historik/nat01-1.txt");
		assertEquals(1, jdbcTemplate.queryForInt("SELECT COUNT(*) FROM VitaminGrunddata"));
	}

	@Test
	public void closesExistingRowAndmakesNewRowWhenImportingSameDrugWithChangedData() throws Exception {
		importFile("data/historik/nat01-1.txt");
		assertEquals(1, jdbcTemplate.queryForInt("SELECT COUNT(*) FROM VitaminGrunddata"));
		long oldestPid = jdbcTemplate.queryForLong("SELECT VitaminGrunddataPID FROM VitaminGrunddata");

		importFile("data/historik/nat01-2.txt");
		assertEquals(2, jdbcTemplate.queryForInt("SELECT COUNT(*) FROM VitaminGrunddata"));
		long newestPid = jdbcTemplate.queryForLong("SELECT VitaminGrunddataPID FROM VitaminGrunddata WHERE ValidTo IS NULL");

		assertEquals(getTimestampFromPersister(), jdbcTemplate.queryForObject("SELECT ValidTo from VitaminGrunddata where VitaminGrunddataPID = ?", Timestamp.class, oldestPid));
		assertEquals(getTimestampFromPersister(), jdbcTemplate.queryForObject("SELECT ValidFrom from VitaminGrunddata where VitaminGrunddataPID = ?", Timestamp.class, newestPid));
		assertEquals(1, jdbcTemplate.queryForInt("SELECT Count(*) from VitaminGrunddata where VitaminGrunddataPID = ? AND ValidTo IS NULL", newestPid)); // assert that the new record has no ValidTo
	}

	@Test
	public void closesExistingRowWhenDrugDisappearsFromDataFile() throws Exception {
		importFile("data/sletning/nat01-1.txt");
		assertEquals(2, jdbcTemplate.queryForInt("SELECT COUNT(*) FROM VitaminGrunddata WHERE ValidTo IS NULL")); // both drugs should be valid

		importFile("data/sletning/nat01-slettet.txt"); // i denne fil findes Drug med DrugID 28116104107 ikke længere
		assertEquals(2, jdbcTemplate.queryForInt("SELECT COUNT(*) FROM VitaminGrunddata")); // no rows should be deleted

		assertEquals(getTimestampFromPersister(), jdbcTemplate.queryForObject("SELECT ValidTo from VitaminGrunddata where DrugID = ?", Timestamp.class, "28116104107"));
	}

	private Timestamp getTimestampFromPersister() {
		DateTime persisterTimeWithMillisTruncated = persister.getTransactionTime().toDateTime().withMillisOfSecond(0);
		return new Timestamp(persisterTimeWithMillisTruncated.getMillis());
	}

	private void importFile(String filePath) throws Exception {
		parser.processSingleFile(new ClassPathResource(filePath).getFile(), VitaminRecordSpecs.GRUNDDATA_RECORD_SPEC);
	}
}
