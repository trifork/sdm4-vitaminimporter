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

import dk.nsi.sdm4.testutils.TestDbConfiguration;
import dk.nsi.sdm4.vitamin.VitaminimporterApplicationTestConfig;
import dk.nsi.sdm4.vitamin.config.VitaminimporterApplicationConfig;
import dk.nsi.sdm4.vitamin.exception.InvalidVitaminDatasetException;
import dk.nsi.sdm4.vitamin.recordspecs.VitaminRecordSpecs;
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

import static org.junit.Assert.*;

/**
 * Test af opførsel vedr validering af dataset og import af et fuldt dataset.
 * Er en integrationstest, dvs. importeren gemmer i databasen
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = {VitaminimporterApplicationTestConfig.class, TestDbConfiguration.class})
public class VitaminParserDatasetsIntegrationTest
{
	@Rule
	public TemporaryFolder tmpDir = new TemporaryFolder();

	@Autowired
	private VitaminParser parser;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	public void shouldComplainIfDatadirIsNotADirectoryAndErrorShouldIndicatePathToDatadir() throws IOException {
		File dataDirWhichIsNotADirectory = new ClassPathResource("data/vitaminer/nat01.txt").getFile();

		try {
			parser.process(dataDirWhichIsNotADirectory, "");
			fail("Expected InvalidVitaminDatasetException, but none came");
		} catch (InvalidVitaminDatasetException e) {
			assertTrue(e.getMessage().contains("not a directory"));
			assertTrue(e.getMessage().contains(dataDirWhichIsNotADirectory.getAbsolutePath()));
		}
	}

	@Test
	public void shouldComplainIfDatadirIsNull() throws IOException {
		try {
			parser.process(null, "");
			fail("Expected InvalidVitaminDatasetException, but none came");
		} catch (InvalidVitaminDatasetException e) {
			assertTrue(e.getMessage().contains("null"));
		}
	}

	@Test
	public void shouldComplainIfDatadirIsNotReadableAndErrorShouldIndicatePathToDatadir() throws IOException {
		File unreadableDataset = tmpDir.newFolder();
		assertTrue(unreadableDataset.setReadable(false)); // the assert is just to make sure we can set the permission, not part of the test

		try {
			parser.process(unreadableDataset, "");
			fail("Expected InvalidVitaminDatasetException, but none came");
		} catch (InvalidVitaminDatasetException e) {
			assertTrue(e.getMessage().contains("is not readable"));
			assertTrue(e.getMessage().contains(unreadableDataset.getAbsolutePath()));
		}
	}


	@Test
	public void shouldComplainIfFilesAreMissingFromDataDir() throws IOException {
		File dataDirWithMissingFile = new ClassPathResource("data/vitaminer-mangler-en-fil").getFile();

		try {
			parser.process(dataDirWithMissingFile, "");
			fail("Expected InvalidVitaminDatasetException, but none came");
		} catch (InvalidVitaminDatasetException e) {
			assertTrue(e.getMessage().contains("nat10.txt")); // det er den fil der mangler
		}
	}

	@Test
	public void shouldNotComplainIfDataDirContainsExtraFiles() throws IOException {
		File dataDirWithExtraFile = new ClassPathResource("data/vitaminer-med-slet-fil").getFile();
		parser.process(dataDirWithExtraFile, "");
	}

	@Test
	public void shouldImportAllThree01txtFiles() throws IOException {
		File dataDirWithExtraFile = new ClassPathResource("data/vitaminer-med-alle-01").getFile();
		parser.process(dataDirWithExtraFile, "");
		assertEquals(3, jdbcTemplate.queryForInt("SELECT COUNT(*) FROM " + VitaminRecordSpecs.GRUNDDATA_RECORD_SPEC.getTable()));
	}
}
