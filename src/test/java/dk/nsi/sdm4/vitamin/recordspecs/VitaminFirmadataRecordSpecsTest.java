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
package dk.nsi.sdm4.vitamin.recordspecs;

import dk.nsi.sdm4.core.parser.SingleLineRecordParser;
import dk.nsi.sdm4.core.persistence.recordpersister.Record;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unittest der kontrollerer parsning af eksempel datalinjer for Firmadata
 */
public class VitaminFirmadataRecordSpecsTest {
	private SingleLineRecordParser grunddataParser;
	private Record record;
	private Record recordWithAllData;

	@Before
	public void makeParsers() {
		grunddataParser = new SingleLineRecordParser(VitaminRecordSpecs.FIRMADATA_RECORD_SPEC);
		String line = "257447Odense Universitets Hospital                          ";
		record = grunddataParser.parseLine(line);

		String lineWithAllData = "257447Odense Universitets Hospital    OUH                 OU"; // this line is constructed, kortFirmaMaerke and ParallelimportKode is never set in the data from LMS
		recordWithAllData = grunddataParser.parseLine(lineWithAllData);
	}

	@Test
	public void parsesFirmanummer() {
		assertEquals(257447L, record.get("firmaID"));
	}

	@Test
	public void parseslangtFirmaMaerke() {
		assertEquals("Odense Universitets Hospital", record.get("langtFirmaMaerke"));
	}

	@Test
	public void leavesKortFirmaMaerkeBlankWhenItsBlankInTheDataLine() {
		// it's blank because none of the data seems to include it, not because we can't parse it
		assertEquals("", record.get("kortFirmaMaerke"));
	}

	@Test
	public void parsesKortFirmaMaerke() {
		// it's blank because none of the data seems to include it, not because we can't parse it
		assertEquals("OUH", recordWithAllData.get("kortFirmaMaerke"));
	}

	@Test
	public void leavesParallelimportKodeBlankWhenItsBlankInTheDataLine() {
		// it's blank because none of the data seems to include it, not because we can't parse it
		assertEquals("", record.get("parallelimportKode"));
	}

	@Test
	public void parsesParallelImportKode() {
		// it's blank because none of the data seems to include it, not because we can't parse it
		assertEquals("OU", recordWithAllData.get("parallelimportKode"));
	}
}
