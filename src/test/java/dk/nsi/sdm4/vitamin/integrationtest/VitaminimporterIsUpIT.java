package dk.nsi.sdm4.vitamin.integrationtest;

import dk.nsi.sdm4.testutils.StatuspageChecker;
import org.junit.Test;

public class VitaminimporterIsUpIT {
	@Test
	public void statusPageReturns200OK() throws Exception {
		new StatuspageChecker("vitaminimporter").assertThatStatuspageReturns200OK();
	}
}
