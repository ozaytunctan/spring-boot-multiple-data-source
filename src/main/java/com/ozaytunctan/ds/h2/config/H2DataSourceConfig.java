package com.ozaytunctan.ds.h2.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {
		"com.ozaytunctan.ds.h2.repository" }, entityManagerFactoryRef = "h2EntityManager", transactionManagerRef = "h2TransactionManager")
public class H2DataSourceConfig {

	@Autowired
	private Environment env;

	
	 @Bean("securityDataSourceProperties")
     @ConfigurationProperties(prefix="spring.h2.datasource")
     public DataSourceProperties securityDataSourceProperties() {
         return new DataSourceProperties();
     }
     
	 @Bean("h2DataSource")
     public DataSource securityDataSource() {
          DataSourceProperties securityDataSourceProperties = securityDataSourceProperties();
          return DataSourceBuilder.create()
         .driverClassName(securityDataSourceProperties.getDriverClassName())
         .url(securityDataSourceProperties.getUrl())
         .username(securityDataSourceProperties.getUsername())
         .password(securityDataSourceProperties.getPassword())
         .build();
     }

	@Bean("h2EntityManager")
	public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean(
		 @Qualifier("h2DataSource") DataSource dataSource) {

		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
		jpaProperties.put("hibernate.show-sql", env.getProperty("spring.jpa.show-sql"));
		jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.SQLServer2012Dialect");

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(dataSource);
		factory.setJpaProperties(jpaProperties);
		factory.setPackagesToScan("com.ozaytunctan.ds.h2.model");
		factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		return factory;
	}

	@Bean("h2TransactionManager")
	public PlatformTransactionManager platformTransactionManager(
			@Qualifier("h2EntityManager") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}

}
