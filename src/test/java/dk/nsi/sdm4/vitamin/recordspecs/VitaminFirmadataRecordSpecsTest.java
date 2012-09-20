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
package dk.nsi.sdm4.vitamin.recordspecs;

import dk.nsi.sdm4.core.parser.SingleLineRecordParser;
import dk.nsi.sdm4.core.persistence.recordpersister.Record;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
