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
