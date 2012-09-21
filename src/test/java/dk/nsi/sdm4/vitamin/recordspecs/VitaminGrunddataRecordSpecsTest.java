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
