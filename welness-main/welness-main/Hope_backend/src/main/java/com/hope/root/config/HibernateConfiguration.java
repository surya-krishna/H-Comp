package com.hope.root.config;


	import java.util.Properties;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
	 
	import org.springframework.beans.factory.annotation.Value;
	import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
	import org.springframework.context.annotation.Bean;
	import org.springframework.context.annotation.Configuration;
	import org.springframework.jdbc.datasource.DriverManagerDataSource;
	import org.springframework.orm.hibernate5.HibernateTransactionManager;
	import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
	import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
	import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
	import org.springframework.transaction.annotation.EnableTransactionManagement;
	 
	@Configuration
	@EnableTransactionManagement
	public class HibernateConfiguration {
	    @Value("${db.driver}")
	    private String DRIVER;
	 
	    @Value("${db.password}")
	    private String PASSWORD;
	 
	    @Value("${db.url}")
	    private String URL;
	 
	    @Value("${db.username}")
	    private String USERNAME;
	 
	    @Value("${hibernate.dialect}")
	    private String DIALECT;
	 
	    @Value("${hibernate.show_sql}")
	    private String SHOW_SQL;
	 
	/*
	 * @Value("${hibernate.hbm2ddl.auto}") private String HBM2DDL_AUTO;
	 */
	    @Value("${entitymanager.packagesToScan}")
	    private String PACKAGES_TO_SCAN;
	 
	    @Bean
	    public DataSource dataSource() {
	        DriverManagerDataSource dataSource = new DriverManagerDataSource();
	        dataSource.setDriverClassName(DRIVER);
	        dataSource.setUrl(URL);
	        dataSource.setUsername(USERNAME);
	        dataSource.setPassword(PASSWORD);
	        return dataSource;
	    }
	 
	    @Bean
	    public LocalSessionFactoryBean sessionFactory() {
	        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
	        sessionFactory.setDataSource(dataSource());
	        sessionFactory.setPackagesToScan(PACKAGES_TO_SCAN);
	        Properties hibernateProperties = new Properties();
	        hibernateProperties.put("hibernate.dialect", DIALECT);
	        hibernateProperties.put("hibernate.show_sql", SHOW_SQL);
	        hibernateProperties.put("hibernate.hbm2ddl.auto", "update");
	        sessionFactory.setHibernateProperties(hibernateProperties);
	 
	        return sessionFactory;
	    }
	 
	    @Bean
	    public HibernateTransactionManager transactionManager() {
	        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
	        transactionManager.setSessionFactory(sessionFactory().getObject());
	        return transactionManager;
	    }  
	    
	   /* @Bean
	    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
	        //JpaVendorAdapteradapter can be autowired as well if it's configured in application properties.
	        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	        vendorAdapter.setGenerateDdl(false);

	        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
	        factory.setJpaVendorAdapter(vendorAdapter);
	        //Add package to scan for entities.
	        factory.setPackagesToScan(PACKAGES_TO_SCAN);
	        factory.setDataSource(this.dataSource());
	        return factory;
	    }
*/
	    
	}
