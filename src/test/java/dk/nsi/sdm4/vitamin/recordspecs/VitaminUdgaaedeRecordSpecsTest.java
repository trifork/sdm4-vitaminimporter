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
