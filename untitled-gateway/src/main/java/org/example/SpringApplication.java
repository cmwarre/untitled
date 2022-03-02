package org.example;

import com.inductiveautomation.ignition.common.util.LoggerEx;
import com.inductiveautomation.ignition.gateway.datasource.records.JDBCDriverRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages = "org.example.repository")
@ComponentScan(basePackages = "org.example")
public class SpringApplication {

    private static final LoggerEx logger = LoggerEx.newBuilder().build(SpringApplication.class);

    private void configureDialect(JDBCDriverRecord driver, org.hibernate.cfg.Configuration config) {
        switch (driver.getVendor()) {
            case MSSQL:
                config.setProperty("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");
                break;
            case MYSQL:
                if (driver.getClassname().toLowerCase().matches(".*mariadb.*"))
                    config.setProperty("hibernate.dialect", "org.hibernate.dialect.MariaDBDialect");
                else
                    config.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
                break;
            case POSTGRES:
                config.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgresPlusDialect");
                break;
            case ORACLE:
                config.setProperty("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
                break;
            case DB2:
                config.setProperty("hibernate.dialect", "org.hibernate.dialect.DB2Dialect");
                break;
            case SQLITE:
                config.setProperty("hibernate.dialect", "org.sqlite.hibernate.dialect.SQLiteDialect");
                break;
            case FIREBIRD:
                config.setProperty("hibernate.dialect", "org.hibernate.dialect.FirebirdDialect");
                break;
            case GENERIC:
                logger.warn("Cannot setup Hibernate with Generic Dialect.  Current database is not supported");
                break;
        }
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        logger.info("Bootstrapping Entity Factory Manager");
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("org.example.model");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());

        return em;
    }

    @Bean
    public DataSource dataSource() {
        return IgnitionSQLDataSource.getInstance();
    }

    public Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MariaDBDialect");
        properties.setProperty("hibernate.entityManagerFactoryInterface", "javax.persistence.EntityManagerFactory");
        return properties;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

}
