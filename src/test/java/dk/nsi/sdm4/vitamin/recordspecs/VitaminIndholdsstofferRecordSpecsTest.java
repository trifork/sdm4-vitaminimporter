package dk.nsi.sdm4.vitamin.recordspecs;

import dk.nsi.sdm4.core.parser.SingleLineRecordParser;
import dk.nsi.sdm4.core.persistence.recordpersister.Record;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VitaminIndholdsstofferRecordSpecsTest {
	private Record record;

	@Before
	public void makeParsers() {
		SingleLineRecordParser parser = new SingleLineRecordParser(VitaminRecordSpecs.INDHOLDSSTOFFER_RECORD_SPEC);
		String line = "28116170104      savpalmefrugt                                                                                       savpalmefrugt (serenoa repens)                                                                      Savpalmefrugt ekstrakt                                                                                                                                ";
		record = parser.parseLine(line);
	}

	@Test
	public void parsesDrugID() {
		assertEquals(28116170104L, record.get("drugID"));
	}

	@Test
	public void parsesStofklasse() {
		assertEquals("savpalmefrugt", record.get("stofklasse"));
	}

	@Test
	public void parsesSubstansGruppe() {
		assertEquals("savpalmefrugt (serenoa repens)", record.get("substansgruppe"));
	}

	@Test
	public void parsesSubstans() {
		assertEquals("Savpalmefrugt ekstrakt", record.get("substans"));
	}
}
