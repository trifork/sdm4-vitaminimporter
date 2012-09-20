package dk.nsi.sdm4.vitamin.parser;

import org.apache.log4j.Level;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class VitaminParserTest {
	private VitaminParser parser;

	@Before
	public void setup() {
		parser = new VitaminParser();
	}

	@Test
	public void selectDebugLevelWhenSlet02txtFileIsPresentInDatadir() {
		assertEquals(Level.DEBUG, parser.levelForUnexpectedFile(new File("slet02.txt")));
	}

	@Test
	public void selectDebugLevelWhenCompletelyUnexpectedFileIsPresentInDatadir() {
		assertEquals(Level.WARN, parser.levelForUnexpectedFile(new File("CompletelyUnexpected")));
	}

}
