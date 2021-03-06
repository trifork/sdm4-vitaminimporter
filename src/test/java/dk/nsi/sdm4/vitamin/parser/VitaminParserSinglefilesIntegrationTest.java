/**
 * The MIT License
 *
 * Original work sponsored and donated by National Board of e-Health (NSI), Denmark
 * (http://www.nsi.dk)
 *
 * Copyright (C) 2011 National Board of e-Health (NSI), Denmark (http://www.nsi.dk)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package dk.nsi.sdm4.vitamin.parser;

import dk.nsi.sdm4.core.persistence.recordpersister.RecordPersister;
import dk.nsi.sdm4.core.persistence.recordpersister.RecordSpecification;
import dk.nsi.sdm4.testutils.TestDbConfiguration;
import dk.nsi.sdm4.vitamin.VitaminimporterApplicationTestConfig;
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
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Test af opførsel vedr validering af enkeltfiler.
 * Er en integrationstest, dvs. importeren gemmer i databasen
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = {VitaminimporterApplicationTestConfig.class, TestDbConfiguration.class})
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
	    importFile(Long.class, "data/vitaminer/nat01.txt", VitaminRecordSpecs.GRUNDDATA_RECORD_SPEC);

		assertEquals(86, jdbcTemplate.queryForInt("SELECT COUNT(*) FROM VitaminGrunddata"));
	}

	@Test
	public void persistsNameWithNonAsciiCharacters() throws Exception {
		importFile(Long.class, "data/vitaminer/nat01.txt", VitaminRecordSpecs.GRUNDDATA_RECORD_SPEC);

		assertEquals("Drogen`s Stærk Baldrian citrm.", jdbcTemplate.queryForObject("SELECT Navn FROM VitaminGrunddata where DrugID=52610061197", String.class));
		assertEquals("Ægte Venustorn", jdbcTemplate.queryForObject("SELECT Navn FROM VitaminGrunddata where DrugID=52610011893", String.class));
		assertEquals("orale dråber, opløsn", jdbcTemplate.queryForObject("SELECT FormTekst FROM VitaminGrunddata where DrugID=52610016293", String.class));
	}

	@Test
	public void persistsKarantaeneData() throws Exception {
		importFile(Long.class, "data/vitaminer/nat01.txt", VitaminRecordSpecs.GRUNDDATA_RECORD_SPEC);

		assertEquals("20061009", jdbcTemplate.queryForObject("SELECT KarantaeneDato FROM VitaminGrunddata where DrugID=52610057196", String.class));
	}

	@Test
	public void persistsFirmadata() throws Exception {
		importFile(Long.class, "data/vitaminer/rad09.txt", VitaminRecordSpecs.FIRMADATA_RECORD_SPEC);

		assertEquals("Odense Universitets Hospital", jdbcTemplate.queryForObject("SELECT LangtFirmaMaerke FROM VitaminFirmadata where FirmaID=257447", String.class));
	}

	@Test
	public void persistsUdgaaedeNavne() throws Exception {
		importFile(String.class, "data/vitaminer/nat10.txt", VitaminRecordSpecs.UDGAAEDENAVNE_RECORD_SPEC);

		assertEquals("OBBEKJÆRS ORIGINALE PEBERMYNTE GRÆSKARKERNE", jdbcTemplate.queryForObject("SELECT TidligereNavn FROM VitaminUdgaaedeNavne where DrugID=52610024893 and AendringsDato='19961212'", String.class));
	}

	@Test
	public void persistsIndholdsstoffer() throws Exception {
		importFile(String.class, "data/vitaminer/nat30.txt", VitaminRecordSpecs.INDHOLDSSTOFFER_RECORD_SPEC);

		assertEquals("savpalmefrugt (serenoa repens)", jdbcTemplate.queryForObject("SELECT Substansgruppe FROM VitaminIndholdsstoffer where DrugID=28116170104", String.class));
	}

	private Timestamp getTimestampFromPersister() {
		DateTime persisterTimeWithMillisTruncated = persister.getTransactionTime().toDateTime().withMillisOfSecond(0);
		return new Timestamp(persisterTimeWithMillisTruncated.getMillis());
	}

	private <T> void importFile(Class<T> clazz, String filePath, RecordSpecification spec) throws Exception {
		parser.processSingleFile(clazz, new HashSet<T>(), new ClassPathResource(filePath).getFile(), spec);
	}
}
