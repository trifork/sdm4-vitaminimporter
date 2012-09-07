package dk.nsi.sdm4.vitamin.exception;

import dk.nsi.sdm4.core.parser.ParserException;

public class InvalidVitaminDatasetException extends ParserException {
	public InvalidVitaminDatasetException(String message) {
		super(message);
	}
}
