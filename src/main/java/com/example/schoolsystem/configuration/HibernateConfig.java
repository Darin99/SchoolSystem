package com.example.schoolsystem.configuration;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class HibernateConfig {

    @Value(value = "${spring.datasource.username:root}")
    private String dbUsername;

    @Value(value = "${spring.datasource.password:password}")
    private String dbPassword;

    @Value(value = "${spring.datasource.url:dbc:mysql://localhost:3306/school_system_db}")
    private String dbUrl;

    @Value(value = "${spring.datasource.driverClassName:com.mysql.cj.jdbc.Driver}")
    private String dbDriver;

    @Value(value = "${spring.jpa.properties.hibernate.dialect:org.hibernate.dialect.MySQLDialect}")
    private String dbDialect;

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("com.example.schoolsystem.model.entity");
        sessionFactory.setHibernateProperties(hibernateProperties());

        return sessionFactory;
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(dbDriver);
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager hibernateTransactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }

    private final Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.put(Environment.DIALECT, dbDialect);
        hibernateProperties.put(Environment.HBM2DDL_AUTO, "create-drop");
        hibernateProperties.put(Environment.SHOW_SQL, "true");

        return hibernateProperties;
    }
}
