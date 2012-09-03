package dk.nsi.sdm4.vitamin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dk.nsi.sdm4.core.parser.Parser;
import dk.nsi.sdm4.core.persistence.AuditingPersister;
import dk.nsi.sdm4.core.persistence.Persister;
import dk.nsi.sdm4.vitamin.parser.VitaminImporter;

@Configuration
public class VitaminimporterApplicationConfig {
	@Bean
	public Parser parser() {
		return new VitaminImporter();
	}

	@Bean
	public Persister persister() {
		return new AuditingPersister();
	}

}