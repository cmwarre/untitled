package org.example;

import com.inductiveautomation.ignition.common.licensing.LicenseState;
import com.inductiveautomation.ignition.common.util.LoggerEx;
import com.inductiveautomation.ignition.gateway.model.AbstractGatewayModuleHook;
import com.inductiveautomation.ignition.gateway.model.GatewayContext;
import org.example.model.Model;
import org.example.service.ModelService;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayHook extends AbstractGatewayModuleHook {

    private static final LoggerEx logger = LoggerEx.newBuilder().build(GatewayHook.class);
    private GatewayContext gatewayContext;

    @Override
    public void setup(GatewayContext gatewayContext) {
        logger.info("Spring Example is Starting Up");
        this.gatewayContext = gatewayContext;
        IgnitionSQLDataSource.initialize(gatewayContext);
    }

    private AnnotationConfigApplicationContext springContext;
    private AutowireCapableBeanFactory beanFactory;

    @Override
    public void startup(LicenseState licenseState) {
        springContext = new AnnotationConfigApplicationContext(SpringApplication.class);
        beanFactory = springContext.getAutowireCapableBeanFactory();

        var service = beanFactory.getBean(ModelService.class);
        service.save(new Model("test"));
        service.getAll().forEach(model -> logger.info(model.toString()));
    }

    @Override
    public void shutdown() {
        logger.info("Spring Example is Shutting Down");
        if(springContext != null)
            springContext.close();
    }

}
