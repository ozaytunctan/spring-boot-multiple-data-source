package com.ozaytunctan.ds.mssql.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * JPA Database config
 * 
 * @author otunctan
 *
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {
		"com.ozaytunctan.ds.mssql.repository" }, entityManagerFactoryRef = "msSqlEntityManager", transactionManagerRef = "msSqlTransactionManager")
public class MsSQLDataSourceConfig {

	
	@Autowired
	private Environment env;
	
//	@Primary
//	@Bean("msSqlDataSource")
//	@ConfigurationProperties(prefix = "spring.mssql.datasource")
//	public DataSource dataSource() {
//		return DataSourceBuilder.create().build();
//	}
	
	 @Primary
	 @Bean("msSqlDataSourceProperties")
     @ConfigurationProperties(prefix="spring.mssql.datasource")
     public DataSourceProperties securityDataSourceProperties() {
         return new DataSourceProperties();
     }
     @Primary
	 @Bean("msSqlDataSource")
     public DataSource securityDataSource(@Qualifier("msSqlDataSourceProperties") DataSourceProperties msSqlDataSourceProperties) {
          
          return DataSourceBuilder.create()
         .driverClassName(msSqlDataSourceProperties.getDriverClassName())
         .url(msSqlDataSourceProperties.getUrl())
         .username(msSqlDataSourceProperties.getUsername())
         .password(msSqlDataSourceProperties.getPassword())
         .build();
     }

	@Primary
	@Bean("msSqlEntityManager")
	public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean(@Qualifier("msSqlDataSource") DataSource dataSource) {
		// HIBERNATE CONFIG
		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
		jpaProperties.put("hibernate.show-sql", env.getProperty("spring.jpa.show-sql"));
		jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.SQLServer2012Dialect");

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(dataSource);
		factory.setJpaProperties(jpaProperties);
		factory.setPackagesToScan("com.ozaytunctan.ds.mssql.model");
		factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

		return factory;
	}

	@Primary
	@Bean("msSqlTransactionManager")
	public PlatformTransactionManager platformTransactionManager(
			@Qualifier("msSqlEntityManager") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}

}
