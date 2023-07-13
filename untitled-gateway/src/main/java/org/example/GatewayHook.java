package org.example;

import com.inductiveautomation.ignition.common.licensing.LicenseState;
import com.inductiveautomation.ignition.common.util.LoggerEx;
import com.inductiveautomation.ignition.gateway.model.AbstractGatewayModuleHook;
import com.inductiveautomation.ignition.gateway.model.GatewayContext;
import org.example.api.ModelScriptAPI;
import org.example.components.IgnitionSQLDataSource;
import org.example.model.Model;
import org.example.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@EnableJpaRepositories(basePackages = "org.example.repository")
@EnableTransactionManagement
@ComponentScan(basePackages = "org.example")
public class GatewayHook extends AbstractGatewayModuleHook {

    private static final LoggerEx logger = LoggerEx.newBuilder().build(GatewayHook.class);
    private GatewayContext gatewayContext;
    private AnnotationConfigApplicationContext springContext;

    @Override
    public void setup(GatewayContext gatewayContext) {
        logger.info("Spring Example is Starting Up");
        this.gatewayContext = gatewayContext;
    }

    @Override
    public void startup(LicenseState licenseState) {

        // force jpa to use the current classes classLoader to load spring dependencies
        var threadClassLoader = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(getClass().getClassLoader());

        springContext = new AnnotationConfigApplicationContext();
        springContext.getBeanFactory().registerSingleton("gatewayContext", gatewayContext);

        springContext.getBeanFactory().registerResolvableDependency(GatewayContext.class, gatewayContext);

        springContext.register(GatewayHook.class);
        springContext.refresh();

        ModelScriptAPI modelScriptAPI = springContext.getAutowireCapableBeanFactory().getBean(ModelScriptAPI.class);
        modelScriptAPI.test();

        ModelService modelService = springContext.getAutowireCapableBeanFactory().getBean(ModelService.class);
        modelService.save(new Model("test"));
        modelService.getAll().forEach(model -> logger.info(model.getName()));

        Thread.currentThread().setContextClassLoader(threadClassLoader); // reset the class loader to prevent any unintended side effects
    }

    @Override
    public void shutdown() {
        logger.info("Spring Example is Shutting Down");
        if (springContext != null) {
            springContext.close();
        }
    }

    @Bean
    @Autowired
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(IgnitionSQLDataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("org.example.model");  // Set the package of your @Entity classes.

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        Properties properties = new Properties();
        // Set any additional Hibernate properties here.
        properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MariaDBDialect");
        em.setJpaProperties(properties);

        return em;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

}

