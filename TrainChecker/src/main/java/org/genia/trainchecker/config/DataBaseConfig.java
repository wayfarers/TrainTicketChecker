package org.genia.trainchecker.config;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.ejb.HibernatePersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("org.genia.trainchecker.repository")	//add spring-data
@ComponentScan(basePackages = {"org.genia.trainchecker"})
public class DataBaseConfig {

	@Bean
	public DataSource getDataSource() {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost/uzgovua");
		ds.setUsername("test");
		ds.setPassword("test");
		return ds;
	}
	
	@Bean
	public EntityManagerFactory entityManagerFactory() {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//		vendorAdapter.setGenerateDdl(true);
//		vendorAdapter.setDatabasePlatform("mysql");
		
		
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setPackagesToScan("org.genia.trainchecker.entity");
		emf.setDataSource(getDataSource());
		emf.setJpaDialect(new HibernateJpaDialect());
		emf.setJpaVendorAdapter(vendorAdapter);
		emf.afterPropertiesSet();
		return emf.getObject();
	}
	
	@Bean
	public PlatformTransactionManager transactionManager() throws Exception {
		EntityManagerFactory factory = entityManagerFactory();
		JpaTransactionManager tm = new JpaTransactionManager();
		System.out.println(factory);
		tm.setEntityManagerFactory(factory);
		return tm;
//		return new JpaTransactionManager( factory );
	}
	
	@Bean
    public EntityManager entityManager() throws Exception {
        return entityManagerFactory().createEntityManager();
    }
}
