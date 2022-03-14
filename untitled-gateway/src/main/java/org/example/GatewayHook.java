package org.example;

import com.inductiveautomation.ignition.common.licensing.LicenseState;
import com.inductiveautomation.ignition.common.util.LoggerEx;
import com.inductiveautomation.ignition.gateway.model.AbstractGatewayModuleHook;
import com.inductiveautomation.ignition.gateway.model.GatewayContext;
import org.example.components.IgnitionSQLDataSource;
import org.example.model.Model;
import org.example.repository.ModelRepo;
import org.example.service.ModelService;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "org.example")
public class GatewayHook extends AbstractGatewayModuleHook {

    private static final LoggerEx logger = LoggerEx.newBuilder().build(GatewayHook.class);
    private GatewayContext gatewayContext;

    @Override
    public void setup(GatewayContext gatewayContext) {
        logger.info("Spring Example is Starting Up");
        this.gatewayContext = gatewayContext;
    }

    private AnnotationConfigApplicationContext springContext;
    private AutowireCapableBeanFactory beanFactory;

    @Override
    public void startup(LicenseState licenseState) {
        springContext = new AnnotationConfigApplicationContext(GatewayHook.class);
        springContext.scan("org.example.model", "org.example.repository", "org.example.components"); // scan for Spring components
        springContext.register(ModelRepo.class);
        springContext.register(ModelService.class);
        beanFactory = springContext.getAutowireCapableBeanFactory();

        var service = beanFactory.getBean(ModelService.class);
        service.save(new Model("test"));
        service.getAll().forEach(model -> logger.info(model.toString()));
    }

    @Override
    public void shutdown() {
        logger.info("Spring Example is Shutting Down");
        if (springContext != null) {
            springContext.close();
        }
    }

    @Bean
    public javax.persistence.EntityManagerFactory entityManagerFactory() {
        logger.info("Bootstrapping Entity Factory Manager");
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();

        em.setDataSource(dataSource());
        em.setPackagesToScan("org.example.model");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());

        return em.getObject();
    }

    @Bean
    public GatewayContext gatewayContext() {
        return gatewayContext;
    }

    @Bean
    public DataSource dataSource() {
        return new IgnitionSQLDataSource(gatewayContext);
    }

    public Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MariaDBDialect");
        return properties;
    }

//    @Bean
//    public PlatformTransactionManager transactionManager() {
//        final JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(entityManagerFactory());
//        return transactionManager;
//    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

}

