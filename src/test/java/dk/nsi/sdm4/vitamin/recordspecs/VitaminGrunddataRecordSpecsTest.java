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

public class VitaminGrunddataRecordSpecsTest {
	private SingleLineRecordParser grunddataParser;
	private Record record;

	@Before
	public void makeParsers() {
		grunddataParser = new SingleLineRecordParser(VitaminRecordSpecs.GRUNDDATA_RECORD_SPEC);
		String line ="28116103706SPNA              EcovagFlora                   vaginalkapsler,hårdeVAGKAPH                           0000000000   011366011366 G01AX14VA                                ";
		record = grunddataParser.parseLine(line);
	}

	@Test
	public void parsesDrugID() {
		assertEquals(28116103706L, record.get("drugID"));
	}

	@Test
	public void parsesVaretype() {
		assertEquals("SP", record.get("varetype"));
	}

	@Test
	public void parsesVaredeltype() {
		assertEquals("NA", record.get("varedeltype"));
	}

	@Test
	public void parsesAlfabetSekvensplads() {
		assertEquals("", record.get("alfabetSekvensplads"));
	}

	@Test
	public void parsesSpecNummer() {
		assertEquals("", record.get("specNummer"));
	}

	@Test
	public void parsesNavn() {
		assertEquals("EcovagFlora", record.get("navn"));
	}

	@Test
	public void parsesFormTekst() {
		assertEquals("vaginalkapsler,hårde", record.get("formTekst"));
	}

	@Test
	public void parsesFormKode() {
		assertEquals("VAGKAPH", record.get("formKode"));
	}

	@Test
	public void parsesKodeYderligereFormOplysinger() {
		assertEquals("", record.get("kodeYderligereFormOplysninger"));
	}

	@Test
	public void parsesStyrkeTekst() {
		assertEquals("", record.get("styrkeTekst"));
	}

	@Test
	public void parsesStyrkeNumerisk() {
		assertEquals(0.0, record.get("styrkeNumerisk"));
	}

	@Test
	public void parsesStyrkeEnhed() {
		assertEquals("", record.get("styrkeEnhed"));
	}

	@Test
	public void parsesMtIndehaverKode() {
		assertEquals(11366L, record.get("mtIndehaverKode"));
	}

	@Test
	public void parsesRepraesentantDistributoerKode() {
		assertEquals(11366L, record.get("repraesentantDistributoerKode"));
	}

	@Test
	public void parsesAtcKode() {
		assertEquals("G01AX14", record.get("atcKode"));
	}

	@Test
	public void parsesAdministrationsvejKode() {
		assertEquals("VA", record.get("administrationsvejKode"));
	}

	@Test
	public void parsesKarantaeneDato() {
		assertEquals("", record.get("karantaeneDato"));
	}

}
