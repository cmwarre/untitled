package org.example;

import com.inductiveautomation.ignition.common.licensing.LicenseState;
import com.inductiveautomation.ignition.common.util.LoggerEx;
import com.inductiveautomation.ignition.gateway.model.AbstractGatewayModuleHook;
import com.inductiveautomation.ignition.gateway.model.GatewayContext;
import org.example.api.ModelScriptAPI;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
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

        Thread.currentThread().setContextClassLoader(threadClassLoader); // reset the class loader to prevent any unintended side effects
    }

    @Override
    public void shutdown() {
        logger.info("Spring Example is Shutting Down");
        if (springContext != null) {
            springContext.close();
        }
    }



}

