package org.genia.trainchecker.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = {"org.genia.trainchecker"})
public class WebConfig {
	
	public VelocityConfigurer velocityConfig() {
		VelocityConfigurer conf = new VelocityConfigurer();
		conf.setResourceLoader(new FileSystemResourceLoader());
		conf.setResourceLoaderPath("/WEB-INF/html/");
		return conf;
	}

}
