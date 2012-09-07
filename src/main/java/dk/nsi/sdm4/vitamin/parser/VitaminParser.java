package dk.nsi.sdm4.vitamin.parser;

import dk.nsi.sdm4.core.parser.Parser;
import dk.nsi.sdm4.core.parser.ParserException;
import dk.nsi.sdm4.core.parser.SingleLineRecordParser;
import dk.nsi.sdm4.core.persistence.recordpersister.Record;
import dk.nsi.sdm4.core.persistence.recordpersister.RecordFetcher;
import dk.nsi.sdm4.core.persistence.recordpersister.RecordPersister;
import dk.nsi.sdm4.core.persistence.recordpersister.RecordSpecification;
import dk.nsi.sdm4.vitamin.exception.InvalidVitaminDatasetException;
import dk.nsi.sdm4.vitamin.recordspecs.VitaminRecordSpecs;
import dk.sdsd.nsp.slalog.api.SLALogItem;
import dk.sdsd.nsp.slalog.api.SLALogger;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VitaminParser implements Parser {
	@Autowired
	private SLALogger slaLogger;

	@Autowired
	private RecordPersister persister;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private RecordFetcher fetcher;

	private static final String FILE_ENCODING = "CP865";

	public void process(File datadir) throws ParserException {
		SLALogItem slaLogItem = slaLogger.createLogItem("VitaminParser", "All");
		RecordSpecification spec = VitaminRecordSpecs.GRUNDDATA_RECORD_SPEC;

		validateDataset(datadir);

		try {
			processSingleFile(datadir.listFiles()[0], spec);

			slaLogItem.setCallResultOk();
			slaLogItem.store();
		} catch (Exception e) {
			slaLogItem.setCallResultError("VitaminParser failed - Cause: " + e.getMessage());
			slaLogItem.store();

			throw new ParserException(e);
		}
	}

	/**
	 * @throws InvalidVitaminDatasetException if a required file is missing
	 */
	private void validateDataset(File datadir) {
		Set<String> requiredFileNames = new HashSet<String>(Arrays.asList("nat01.txt", "nat09.txt", "nat10.txt", "nat30.txt", "rad01.txt", "rad09.txt", "rad10.txt", "vit01.txt", "vit09.txt", "vit10.txt", "vit30.txt"));
		File[] actualFiles = datadir.listFiles();
		Set<String> actualFilenames = new HashSet<String>();
		for (File actualFile : actualFiles) {
			actualFilenames.add(actualFile.getName());
		}

		requiredFileNames.removeAll(actualFilenames);

		if (!requiredFileNames.isEmpty()) {
			throw new InvalidVitaminDatasetException("Missing files " + requiredFileNames + " from data directory " + datadir.getAbsolutePath());
		}
	}

	void processSingleFile(File file, RecordSpecification spec) throws IOException, SQLException {
		SLALogItem slaLogItem = slaLogger.createLogItem("VitaminParser importer of file", file.getName());

		try {
			Set<Long> drugidsFromFile = parseAndPersistFile(file, spec);
			invalidateRecordsRemovedFromFile(drugidsFromFile, spec);

			slaLogItem.setCallResultOk();
			slaLogItem.store();
		} catch (Exception e) {
			slaLogItem.setCallResultError("VitaminParser failed - Cause: " + e.getMessage());
			slaLogItem.store();

			throw new ParserException(e);
		}
	}

	private Set<Long> parseAndPersistFile(File file, RecordSpecification spec) throws IOException, SQLException {
		SingleLineRecordParser grunddataParser = new SingleLineRecordParser(spec);
		Set<Long> drugidsFromFile = new HashSet<Long>();

		File grunddataFile = file;
		List<String> lines = FileUtils.readLines(grunddataFile, FILE_ENCODING);// files are very small, it's okay to hold them in memory
		for (String line : lines) {
			Record record = grunddataParser.parseLine(line);
			drugidsFromFile.add((Long) record.get(spec.getKeyColumn()));
			Record existingRecord = fetcher.fetchCurrent(record.get(spec.getKeyColumn())+"", spec);
			if (existingRecord != null) {
				if (existingRecord.equals(record)) {
					// no need to do anything
				} else {
					jdbcTemplate.update("UPDATE " + spec.getTable() + " set ValidTo = ? WHERE " + spec.getKeyColumn() + " = ? AND ValidTo IS NULL",
							persister.getTransactionTime().toDateTime().toDate(),
							existingRecord.get(spec.getKeyColumn()));
					persister.persist(record, spec);
				}
			} else {
				persister.persist(record, spec);
			}
		}
		return drugidsFromFile;
	}

	private void invalidateRecordsRemovedFromFile(Set<Long> idsFromFile, RecordSpecification spec) {
		Set<Long> idsFromDb = new HashSet<Long>(jdbcTemplate.queryForList("SELECT " + spec.getKeyColumn() + " from " + spec.getTable() + " WHERE ValidTo IS NULL", Long.class));
		idsFromDb.removeAll(idsFromFile);

		Set<Long> idsToBeRemoved = idsFromDb;
		if (!idsToBeRemoved.isEmpty()) {
			jdbcTemplate.update("UPDATE " + spec.getTable() + " SET ValidTo = ? WHERE " + spec.getKeyColumn() + " IN (" + StringUtils.join(idsToBeRemoved, ',')  + ") AND ValidTo IS NULL", persister.getTransactionTime().toDateTime().toDate());
		}
	}

	@Override
	public String getHome() {
		return "vitaminimporter";
	}
}
