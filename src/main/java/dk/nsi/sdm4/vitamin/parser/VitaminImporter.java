package dk.nsi.sdm4.vitamin.parser;

import dk.nsi.sdm4.core.parser.Parser;
import dk.nsi.sdm4.core.parser.ParserException;
import dk.nsi.sdm4.core.parser.SingleLineRecordParser;
import dk.nsi.sdm4.core.persistence.recordpersister.Record;
import dk.nsi.sdm4.core.persistence.recordpersister.RecordFetcher;
import dk.nsi.sdm4.core.persistence.recordpersister.RecordPersister;
import dk.nsi.sdm4.vitamin.recordspecs.VitaminRecordSpecs;
import dk.sdsd.nsp.slalog.api.SLALogItem;
import dk.sdsd.nsp.slalog.api.SLALogger;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.List;

public class VitaminImporter implements Parser {
	@Autowired
	private SLALogger slaLogger;

	@Autowired
	private RecordPersister persister;

	@Autowired
	private RecordFetcher fetcher;

	private static final String FILE_ENCODING = "CP865";

	public void process(File datadir) throws ParserException {
		SLALogItem slaLogItem = slaLogger.createLogItem("VitaminImporter", "All");

		try {
			SingleLineRecordParser grunddataParser = new SingleLineRecordParser(VitaminRecordSpecs.GRUNDDATA_RECORD_SPEC);

			File grunddataFile = datadir.listFiles()[0];
			List<String> lines = FileUtils.readLines(grunddataFile, FILE_ENCODING);// files are very small, it's okay to hold them in memory
			for (String line : lines) {
				Record record = grunddataParser.parseLine(line);
				Record existingRecord = fetcher.fetchCurrent(record.get("drugID")+"", VitaminRecordSpecs.GRUNDDATA_RECORD_SPEC);
				if (existingRecord != null) {
					if (existingRecord.equals(record)) {
						// no need to do anything
					} else {
						// TODO: update existing record's validTo, insert new record
					}
				} else {
					persister.persist(record, VitaminRecordSpecs.GRUNDDATA_RECORD_SPEC);
				}
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
