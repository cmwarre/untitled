package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inductiveautomation.ignition.gateway.model.GatewayContext;
import org.example.components.IgnitionSQLDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContext;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages = "org.example.repository")
@EnableTransactionManagement
@ComponentScan(basePackages = "org.example")
public class SpringConfig {

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

    @Bean
    public GatewayContext gatewayContext() {
        return GatewayHook.getGatewayContext();
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(ObjectMapper objectMapper) {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        jsonConverter.setObjectMapper(objectMapper);
        jsonConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
        return jsonConverter;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {

            @Override
            public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
                converters.add(mappingJackson2HttpMessageConverter(objectMapper()));
            }

            @Override
            public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
                configurer
                        .defaultContentType(MediaType.APPLICATION_JSON);
            }

        };
    }

    @Bean
    public ServletContext servletContext() {
        return gatewayContext().getWebResourceManager().getWebApplication().getServletContext();
    }


}
