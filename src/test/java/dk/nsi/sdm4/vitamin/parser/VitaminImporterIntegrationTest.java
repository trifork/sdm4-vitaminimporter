package dk.nsi.sdm4.vitamin.parser;

import static org.junit.Assert.assertEquals;

import dk.nsi.sdm4.core.persistence.recordpersister.RecordPersister;
import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import dk.nsi.sdm4.core.persistence.AuditingPersister;
import dk.nsi.sdm4.testutils.TestDbConfiguration;
import dk.nsi.sdm4.vitamin.config.VitaminimporterApplicationConfig;

import java.io.File;
import java.net.URL;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = {VitaminimporterApplicationConfig.class, TestDbConfiguration.class})
public class VitaminImporterIntegrationTest
{
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private VitaminImporter importer;

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

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

	private void importFile(String filePath) throws Exception {
		URL resource = getClass().getClassLoader().getResource(filePath);
		File datasetDir = temporaryFolder.newFolder();
		FileUtils.copyURLToFile(resource, new File(datasetDir, lastPathSegment(filePath)));

		importer.process(datasetDir);
	}

	private String lastPathSegment(String filePath) {
		String[] segments = filePath.split("/");

		return segments[segments.length - 1];
	}

}
