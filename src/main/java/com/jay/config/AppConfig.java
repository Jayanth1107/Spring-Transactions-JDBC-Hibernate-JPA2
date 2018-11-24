 package com.jay.config;

import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = "com.jay")
@PropertySource("classpath:prod.properties")
@EnableTransactionManagement
public class AppConfig {
	
	@Autowired
	private Environment env;
	
	/*@Bean(name="dataSource", destroyMethod="shutdown")
	@Profile("test")
	public DataSource datasourceForTest() {
		return new EmbeddedDatabaseBuilder()
				.generateUniqueName(true)
				.setType(EmbeddedDatabaseType.H2)
				.setScriptEncoding("UTF-8")
				.ignoreFailedDrops(true)
				.addScript("schema.sql")
				.addScripts("data.sql")
				.build();
	}*/
	
	/*@Bean(name="transactionManager")
	@Profile("test")
	public PlatformTransactionManager transactionManagerForTest() {
		return new DataSourceTransactionManager(datasourceForTest());
	}*/
	/*
	@Bean(name="dataSource")
	@Profile("prod")*/
	
	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(env.getProperty("db.driver"));
		dataSource.setUrl(env.getProperty("db.url"));
		dataSource.setUsername(env.getProperty("db.username"));
		dataSource.setPassword(env.getProperty("db.password"));
		
		return dataSource;
	}
	
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setShowSql(true);
		adapter.setGenerateDdl(true);
		adapter.setDatabase(Database.MYSQL);
		return adapter;
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(
			DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
		Properties props = new Properties();
		props.setProperty("hibernate.format_sql", String.valueOf(true));
		props.setProperty("hibernate.hbm2ddl.auto", "create");
		props.setProperty("hibernate.hbm2ddl.import_files", "data.sql");
		
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(dataSource);
		emf.setPackagesToScan("com.jay.entities");
		emf.setJpaVendorAdapter(jpaVendorAdapter);
		emf.setJpaProperties(props);
		
		return emf;
	}

	/*@Bean(name="transactionManager")
	@Profile("prod")*/
	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}
	
	@Bean
	public BeanPostProcessor persistenceTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}
}