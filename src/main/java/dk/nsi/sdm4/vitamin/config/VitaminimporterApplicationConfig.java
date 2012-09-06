package dk.nsi.sdm4.vitamin.config;

import dk.nsi.sdm4.core.parser.Parser;
import dk.nsi.sdm4.core.persistence.recordpersister.RecordFetcher;
import dk.nsi.sdm4.vitamin.parser.VitaminParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VitaminimporterApplicationConfig {
	@Bean
	public Parser parser() {
		return new VitaminParser();
	}

	@Bean
	public RecordFetcher fetcher() {
		return new RecordFetcher();
	}
}