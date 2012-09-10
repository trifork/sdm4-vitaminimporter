package dk.nsi.sdm4.vitamin.integrationtest;

import com.mysql.jdbc.Driver;
import dk.nsi.sdm4.testutils.StatuspageChecker;
import dk.nsi.sdm4.testutils.TestDbConfiguration;
import dk.nsi.sdm4.vitamin.config.VitaminimporterApplicationConfig;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = {AllfilesAreImportedIT.TestConfiguration.class})
public class AllfilesAreImportedIT {
	private static final int MAX_RETRIES=10;

	@Configuration
	@Import({VitaminimporterApplicationConfig.class, TestDbConfiguration.class})
	static class TestConfiguration {
		@Value("${test.mysql.port}")
		private int mysqlPort;
		private String db_username = "root";
		private String db_password = "papkasse";

		@Bean
		@Primary
		public DataSource dataSourceTalkingToRealDatabase() throws Exception {
			String jdbcUrlPrefix = "jdbc:mysql://127.0.0.1:" + mysqlPort + "/";
			return new SimpleDriverDataSource(new Driver(), jdbcUrlPrefix + "sdm_warehouse", db_username, db_password);
		}
	}

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Test
	public void statusPageReturns200OK() throws Exception {
		StatuspageChecker checker = new StatuspageChecker("vitaminimporter");

		StatuspageChecker.StatuspageResult lastResult = null;
		for (int i = 0; i < MAX_RETRIES; i++) {
			lastResult = checker.fetchStatusPage();

			assertEquals(200, lastResult.status);

			if (lastResult.responseBody.contains("SUCCESS")) {
				assertDbContainsExpectedRows();
				return; // alt er godt
			}

			Thread.sleep(5*1000);
		}

		fail("Status page did not contain SUCCESS after " + MAX_RETRIES + ", text was " + lastResult.responseBody);
	}

	private void assertDbContainsExpectedRows() {
		assertEquals("VitaminGrunddata", 194, jdbcTemplate.queryForInt("SELECT COUNT(*) from VitaminGrunddata"));
		assertEquals("VitaminFirmadata", 79, jdbcTemplate.queryForInt("SELECT COUNT(*) from VitaminFirmadata"));
		assertEquals("VitaminUdgaaedeNavne", 141, jdbcTemplate.queryForInt("SELECT COUNT(*) from VitaminUdgaaedeNavne"));
		assertEquals("VitaminIndholdsstoffer", 228, jdbcTemplate.queryForInt("SELECT COUNT(*) from VitaminIndholdsstoffer"));


	}
}
