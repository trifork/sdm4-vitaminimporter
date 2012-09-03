package dk.nsi.sdm4.vitamin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import dk.nsi.sdm4.core.config.StamdataConfiguration;
import dk.sdsd.nsp.slalog.api.SLALogConfig;
import dk.sdsd.nsp.slalog.api.SLALogger;

@Configuration
@EnableScheduling
@EnableTransactionManagement
//The Spring Java Configuration annotations above needs to be on this class, not on the abstract superclass to
// make Spring stop complaining about weird things
public class VitaminimporterInfrastructureConfig extends StamdataConfiguration {
	@Bean
	public SLALogger slaLogger() {
		return new SLALogConfig("Stamdata Vitamin-importer", "vitaminimporter").getSLALogger();
	}
}
