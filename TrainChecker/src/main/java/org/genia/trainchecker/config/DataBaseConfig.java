package org.genia.trainchecker.config;

import java.util.Properties;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("org.genia.trainchecker.repositories")	//add spring-data
@ComponentScan(basePackages = {"org.genia.trainchecker"})
@PropertySource(value = "classpath:database.properties")
public class DataBaseConfig {

    @Inject
    Environment env;

    // TODO: Add c3p0 connection pool
    @Bean
    public DataSource getDataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");

        String url = env.getProperty("jdbc.url");
        url += (url.contains("?") ? "&" : "?") + "useUnicode=yes&characterEncoding=UTF-8";

        ds.setUrl(url);
        ds.setUsername(env.getProperty("jdbc.username"));
        ds.setPassword(env.getProperty("jdbc.password"));
        return ds;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//		vendorAdapter.setShowSql(true);
//		vendorAdapter.setDatabase(Database.MYSQL);
//		vendorAdapter.setGenerateDdl(true);
//		vendorAdapter.setDatabasePlatform("mysql");

        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setPackagesToScan("org.genia.trainchecker.entities");
        emf.setDataSource(getDataSource());
        emf.setJpaDialect(new HibernateJpaDialect());
        emf.setJpaVendorAdapter(vendorAdapter);
        emf.setJpaProperties(additionalProperties());
        emf.afterPropertiesSet();
        return emf.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager() throws Exception {
        EntityManagerFactory factory = entityManagerFactory();
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(factory);
        return tm;
//		return new JpaTransactionManager( factory );
    }

    @Bean
    public EntityManager entityManager() throws Exception {
        return entityManagerFactory().createEntityManager();
    }

    Properties additionalProperties() {
        Properties properties = new Properties();
//        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
        properties.setProperty("hibernate.archive.autodetection", "class");
        properties.setProperty("hibernate.show_sql", "false");
        properties.setProperty("hibernate.ejb.naming_strategy", "org.hibernate.cfg.ImprovedNamingStrategy");
        return properties;
     }
}
