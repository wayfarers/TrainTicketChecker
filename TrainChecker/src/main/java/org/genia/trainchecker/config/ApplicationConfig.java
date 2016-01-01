package org.genia.trainchecker.config;

import org.genia.trainchecker.core.TrainTicketChecker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {"org.genia.trainchecker"})
@PropertySource(value = "classpath:config.properties")
@Import(DataBaseConfig.class)
public class ApplicationConfig {
	
	@Bean
	public TrainTicketChecker trainTicketChecker() {
		return new TrainTicketChecker();
	}
}
