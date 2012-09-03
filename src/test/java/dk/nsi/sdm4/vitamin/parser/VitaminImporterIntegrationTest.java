package dk.nsi.sdm4.vitamin.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import dk.nsi.sdm4.core.persistence.AuditingPersister;
import dk.nsi.sdm4.testutils.TestDbConfiguration;
import dk.nsi.sdm4.vitamin.config.VitaminimporterApplicationConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = {VitaminimporterApplicationConfig.class, TestDbConfiguration.class})
public class VitaminImporterIntegrationTest
{
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private VitaminImporter importer;

	@Autowired
	private AuditingPersister persister;

	@Test
	public void testDummy() throws Exception {
	    assertEquals("Dummy", "Dummy");
	}
}
