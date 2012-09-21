/**
 * The contents of this file are subject to the Mozilla Public
 * License Version 1.1 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of
 * the License at http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS
 * IS" basis, WITHOUT WARRANTY OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * rights and limitations under the License.
 *
 * Contributor(s): Contributors are attributed in the source code
 * where applicable.
 *
 * The Original Code is "Stamdata".
 *
 * The Initial Developer of the Original Code is Trifork Public A/S.
 *
 * Portions created for the Original Code are Copyright 2011,
 * Lægemiddelstyrelsen. All Rights Reserved.
 *
 * Portions created for the FMKi Project are Copyright 2011,
 * National Board of e-Health (NSI). All Rights Reserved.
 */
package dk.nsi.sdm4.vitamin.parser;

import dk.nsi.sdm4.core.persistence.recordpersister.RecordPersister;
import dk.nsi.sdm4.core.persistence.recordpersister.RecordSpecification;
import dk.nsi.sdm4.testutils.TestDbConfiguration;
import dk.nsi.sdm4.vitamin.config.VitaminimporterApplicationConfig;
import dk.nsi.sdm4.vitamin.recordspecs.VitaminRecordSpecs;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Test af opførsel vedr validering af enkeltfiler.
 * Er en integrationstest, dvs. importeren gemmer i databasen
 */
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
	    importFile("data/vitaminer/nat01.txt", VitaminRecordSpecs.GRUNDDATA_RECORD_SPEC);

		assertEquals(86, jdbcTemplate.queryForInt("SELECT COUNT(*) FROM VitaminGrunddata"));
	}

	@Test
	public void persistsNameWithNonAsciiCharacters() throws Exception {
		importFile("data/vitaminer/nat01.txt", VitaminRecordSpecs.GRUNDDATA_RECORD_SPEC);

		assertEquals("Drogen`s Stærk Baldrian citrm.", jdbcTemplate.queryForObject("SELECT Navn FROM VitaminGrunddata where DrugID=52610061197", String.class));
		assertEquals("Ægte Venustorn", jdbcTemplate.queryForObject("SELECT Navn FROM VitaminGrunddata where DrugID=52610011893", String.class));
		assertEquals("orale dråber, opløsn", jdbcTemplate.queryForObject("SELECT FormTekst FROM VitaminGrunddata where DrugID=52610016293", String.class));
	}

	@Test
	public void persistsKarantaeneData() throws Exception {
		importFile("data/vitaminer/nat01.txt", VitaminRecordSpecs.GRUNDDATA_RECORD_SPEC);

		assertEquals("20061009", jdbcTemplate.queryForObject("SELECT KarantaeneDato FROM VitaminGrunddata where DrugID=52610057196", String.class));
	}

	@Test
	public void persistsFirmadata() throws Exception {
		importFile("data/vitaminer/rad09.txt", VitaminRecordSpecs.FIRMADATA_RECORD_SPEC);

		assertEquals("Odense Universitets Hospital", jdbcTemplate.queryForObject("SELECT LangtFirmaMaerke FROM VitaminFirmadata where FirmaID=257447", String.class));
	}

	@Test
	public void persistsUdgaaedeNavne() throws Exception {
		importFile("data/vitaminer/nat10.txt", VitaminRecordSpecs.UDGAAEDENAVNE_RECORD_SPEC);

		assertEquals("OBBEKJÆRS ORIGINALE PEBERMYNTE GRÆSKARKERNE", jdbcTemplate.queryForObject("SELECT TidligereNavn FROM VitaminUdgaaedeNavne where DrugID=52610024893 and AendringsDato='19961212'", String.class));
	}

	@Test
	public void persistsIndholdsstoffer() throws Exception {
		importFile("data/vitaminer/nat30.txt", VitaminRecordSpecs.INDHOLDSSTOFFER_RECORD_SPEC);

		assertEquals("savpalmefrugt (serenoa repens)", jdbcTemplate.queryForObject("SELECT Substansgruppe FROM VitaminIndholdsstoffer where DrugID=28116170104", String.class));
	}

	@Test
	public void makesNoExtraRowsWhenImportingSameDataTwice() throws Exception {
		importFile("data/historik/nat01-1.txt", VitaminRecordSpecs.GRUNDDATA_RECORD_SPEC);
		assertEquals(1, jdbcTemplate.queryForInt("SELECT COUNT(*) FROM VitaminGrunddata"));

		importFile("data/historik/nat01-1.txt", VitaminRecordSpecs.GRUNDDATA_RECORD_SPEC);
		assertEquals(1, jdbcTemplate.queryForInt("SELECT COUNT(*) FROM VitaminGrunddata"));
	}

	@Test
	public void closesExistingRowAndmakesNewRowWhenImportingSameDrugWithChangedData() throws Exception {
		importFile("data/historik/nat01-1.txt", VitaminRecordSpecs.GRUNDDATA_RECORD_SPEC);
		assertEquals(1, jdbcTemplate.queryForInt("SELECT COUNT(*) FROM VitaminGrunddata"));
		long oldestPid = jdbcTemplate.queryForLong("SELECT VitaminGrunddataPID FROM VitaminGrunddata");

		importFile("data/historik/nat01-2.txt", VitaminRecordSpecs.GRUNDDATA_RECORD_SPEC);
		assertEquals(2, jdbcTemplate.queryForInt("SELECT COUNT(*) FROM VitaminGrunddata"));
		long newestPid = jdbcTemplate.queryForLong("SELECT VitaminGrunddataPID FROM VitaminGrunddata WHERE ValidTo IS NULL");

		assertEquals(getTimestampFromPersister(), jdbcTemplate.queryForObject("SELECT ValidTo from VitaminGrunddata where VitaminGrunddataPID = ?", Timestamp.class, oldestPid));
		assertEquals(getTimestampFromPersister(), jdbcTemplate.queryForObject("SELECT ValidFrom from VitaminGrunddata where VitaminGrunddataPID = ?", Timestamp.class, newestPid));
		assertEquals(1, jdbcTemplate.queryForInt("SELECT Count(*) from VitaminGrunddata where VitaminGrunddataPID = ? AND ValidTo IS NULL", newestPid)); // assert that the new record has no ValidTo
	}

	@Test
	public void closesExistingRowWhenDrugDisappearsFromDataFile() throws Exception {
		importFile("data/sletning/nat01-1.txt", VitaminRecordSpecs.GRUNDDATA_RECORD_SPEC);
		assertEquals(2, jdbcTemplate.queryForInt("SELECT COUNT(*) FROM VitaminGrunddata WHERE ValidTo IS NULL")); // both drugs should be valid

		importFile("data/sletning/nat01-slettet.txt", VitaminRecordSpecs.GRUNDDATA_RECORD_SPEC); // i denne fil findes Drug med DrugID 28116104107 ikke længere
		assertEquals(2, jdbcTemplate.queryForInt("SELECT COUNT(*) FROM VitaminGrunddata")); // no rows should be deleted

		assertEquals(getTimestampFromPersister(), jdbcTemplate.queryForObject("SELECT ValidTo from VitaminGrunddata where DrugID = ?", Timestamp.class, "28116104107"));
	}

	private Timestamp getTimestampFromPersister() {
		DateTime persisterTimeWithMillisTruncated = persister.getTransactionTime().toDateTime().withMillisOfSecond(0);
		return new Timestamp(persisterTimeWithMillisTruncated.getMillis());
	}

	private void importFile(String filePath, RecordSpecification spec) throws Exception {
		parser.processSingleFile(new ClassPathResource(filePath).getFile(), spec);
	}
}
