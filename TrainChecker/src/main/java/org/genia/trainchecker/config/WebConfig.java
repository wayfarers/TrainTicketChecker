package org.genia.trainchecker.config;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.AbstractTemplateViewResolver;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;
import org.springframework.web.servlet.view.velocity.VelocityViewResolver;

@EnableWebMvc
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = { "org.genia.trainchecker" })
public class WebConfig extends WebMvcConfigurerAdapter{

	@Bean
	public VelocityConfigurer velocityConfig() {
		VelocityConfigurer conf = new VelocityConfigurer();
		conf.setResourceLoader(new FileSystemResourceLoader());
		conf.setResourceLoaderPath("/views/");
		return conf;
	}

	@Bean
	public AbstractTemplateViewResolver velocityViewResolver() {
		AbstractTemplateViewResolver resolver = new VelocityViewResolver();
		resolver.setCache(true);
		resolver.setContentType("text/html; charset=utf-8");
		resolver.setPrefix("");
		resolver.setSuffix(".html");
		resolver.setExposeSpringMacroHelpers(true);
		return resolver;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/").setCachePeriod(31556926);
		registry.addResourceHandler("/views/**").addResourceLocations("/views/");
		registry.addResourceHandler("/components/**").addResourceLocations("/resources/bower_components/");
	}
	
//	@Override
//	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//	    super.configureMessageConverters(converters);
//	    converters.add(responseBodyConverter());
//	}
//	
//	public HttpMessageConverter<String> responseBodyConverter() {
//	    StringHttpMessageConverter converter = new StringHttpMessageConverter();
//	    converter.setSupportedMediaTypes(Arrays.asList(new MediaType("text", "plain", Charset.forName("UTF-8"))));
//	    return converter;
//	}
}
