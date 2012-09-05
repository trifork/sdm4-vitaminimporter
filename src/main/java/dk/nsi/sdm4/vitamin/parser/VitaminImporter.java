package dk.nsi.sdm4.vitamin.parser;

import java.io.File;
import java.util.List;

import dk.nsi.sdm4.core.parser.SingleLineRecordParser;
import dk.nsi.sdm4.core.persistence.AuditingPersister;
import dk.nsi.sdm4.core.persistence.recordpersister.Record;
import dk.nsi.sdm4.core.persistence.recordpersister.RecordPersister;
import dk.nsi.sdm4.vitamin.recordspecs.VitaminRecordSpecs;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import dk.nsi.sdm4.core.parser.Parser;
import dk.nsi.sdm4.core.parser.ParserException;
import dk.nsi.sdm4.core.persistence.Persister;
import dk.sdsd.nsp.slalog.api.SLALogItem;
import dk.sdsd.nsp.slalog.api.SLALogger;

public class VitaminImporter implements Parser {
	@Autowired
	private SLALogger slaLogger;

	@Autowired
	private RecordPersister persister;
	private static final String FILE_ENCODING = "CP865";

	public void process(File datadir) throws ParserException {
		SLALogItem slaLogItem = slaLogger.createLogItem("VitaminImporter", "All");

		try {
			SingleLineRecordParser grunddataParser = new SingleLineRecordParser(VitaminRecordSpecs.GRUNDDATA_RECORD_SPEC);

			File grunddataFile = datadir.listFiles()[0];
			List<String> lines = FileUtils.readLines(grunddataFile, FILE_ENCODING);// files are very small, it's okay to hold them in memory
			for (String line : lines) {
				Record record = grunddataParser.parseLine(line);
				persister.persist(record, VitaminRecordSpecs.GRUNDDATA_RECORD_SPEC);
			}

			slaLogItem.setCallResultOk();
			slaLogItem.store();
		} catch (Exception e) {
			slaLogItem.setCallResultError("VitaminImporter failed - Cause: " + e.getMessage());
			slaLogItem.store();

			throw new ParserException(e);
		}
	}

	@Override
	public String getHome() {
		return "vitaminimporter";
	}
}
