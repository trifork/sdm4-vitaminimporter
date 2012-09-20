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

public class VitaminUdgaaedeRecordSpecsTest {
	private SingleLineRecordParser grunddataParser;
	private Record record;

	@Before
	public void makeParsers() {
		grunddataParser = new SingleLineRecordParser(VitaminRecordSpecs.UDGAAEDENAVNE_RECORD_SPEC);
		String line = "5261000959320060615Thea-Bona                                         ";
		record = grunddataParser.parseLine(line);
	}

	@Test
	public void parsesDrugID() {
		assertEquals(52610009593L, record.get("drugID"));
	}

	@Test
	public void parsesAendringsDato() {
		assertEquals("20060615", record.get("aendringsDato"));
	}
	@Test
	public void parsesTidligereNavn() {
		assertEquals("Thea-Bona", record.get("tidligereNavn"));
	}
}
