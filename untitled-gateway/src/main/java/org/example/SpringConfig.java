package org.example;

import com.inductiveautomation.ignition.gateway.model.GatewayContext;
import org.example.api.rest.security.IgnitionBasicAuthProvider;
import org.example.components.IgnitionSQLDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages = "org.example.repository")
@EnableTransactionManagement
@ComponentScan(basePackages = "org.example")
@EnableWebSecurity
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
    public IgnitionBasicAuthProvider ignitionBasicAuthProvider(GatewayContext gatewayContext) {
        return new IgnitionBasicAuthProvider(gatewayContext);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, GatewayContext gatewayContext) throws Exception {

        http
                .csrf().disable()
                .authenticationProvider(ignitionBasicAuthProvider(gatewayContext))
                .authorizeRequests()
                .antMatchers("/**")
                .authenticated()
                .and()
                .httpBasic()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

}
