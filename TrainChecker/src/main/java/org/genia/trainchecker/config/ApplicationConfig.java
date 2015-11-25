package org.genia.trainchecker.config;

import org.genia.trainchecker.core.TrainTicketChecker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"org.genia.trainchecker"})
public class ApplicationConfig {
	
	@Bean
	public TrainTicketChecker trainTicketChecker() {
		return new TrainTicketChecker();
	}

}
