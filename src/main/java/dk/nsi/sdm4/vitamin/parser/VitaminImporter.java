package dk.nsi.sdm4.vitamin.parser;

import java.io.File;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import dk.nsi.sdm4.core.parser.Parser;
import dk.nsi.sdm4.core.parser.ParserException;
import dk.nsi.sdm4.core.persistence.Persister;
import dk.sdsd.nsp.slalog.api.SLALogItem;
import dk.sdsd.nsp.slalog.api.SLALogger;

public class VitaminImporter implements Parser {
	private static final Logger log = Logger.getLogger(VitaminImporter.class);

	@Autowired
	private SLALogger slaLogger;

	@Autowired
	private Persister persister;

	public void process(File datadir) throws ParserException {
		SLALogItem slaLogItem = slaLogger.createLogItem("VitaminImporter", "All");

		try {
		    // TODO: Make parser 
		    
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
