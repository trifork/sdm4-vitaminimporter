/**
 * The contents of this file are subject to the Mozilla Public
 * License Version 1.1 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of
 * the License at http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS
 * IS" basis, WITHOUT WARRANTY OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * rights and limitations under the License.
 *
 * Contributor(s): Contributors are attributed in the source code
 * where applicable.
 *
 * The Original Code is "Stamdata".
 *
 * The Initial Developer of the Original Code is Trifork Public A/S.
 *
 * Portions created for the Original Code are Copyright 2011,
 * Lægemiddelstyrelsen. All Rights Reserved.
 *
 * Portions created for the FMKi Project are Copyright 2011,
 * National Board of e-Health (NSI). All Rights Reserved.
 */
package dk.nsi.sdm4.vitamin.integrationtest;

import com.mysql.jdbc.Driver;
import dk.nsi.sdm4.testutils.StatuspageChecker;
import dk.nsi.sdm4.testutils.TestDbConfiguration;
import dk.nsi.sdm4.vitamin.config.VitaminimporterApplicationConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Udfører en import på integrationstest vagrant-vm'en og tjekker derefter databasen
 */
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
	public void afterImportDatabaseContainsTheExpectedNumberOfEntities() throws Exception {
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

		fail("Status page did not contain SUCCESS after " + MAX_RETRIES + (lastResult != null ? ", text was " + lastResult.responseBody : ""));
	}

	private void assertDbContainsExpectedRows() {
		assertEquals("VitaminGrunddata", 194, jdbcTemplate.queryForInt("SELECT COUNT(*) from VitaminGrunddata"));
		assertEquals("VitaminFirmadata", 79, jdbcTemplate.queryForInt("SELECT COUNT(*) from VitaminFirmadata"));
		assertEquals("VitaminUdgaaedeNavne", 141, jdbcTemplate.queryForInt("SELECT COUNT(*) from VitaminUdgaaedeNavne"));
		assertEquals("VitaminIndholdsstoffer", 228, jdbcTemplate.queryForInt("SELECT COUNT(*) from VitaminIndholdsstoffer"));


	}
}
