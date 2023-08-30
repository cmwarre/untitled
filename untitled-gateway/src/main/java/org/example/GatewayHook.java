package org.example;

import com.inductiveautomation.ignition.common.licensing.LicenseState;
import com.inductiveautomation.ignition.common.util.LoggerEx;
import com.inductiveautomation.ignition.gateway.model.AbstractGatewayModuleHook;
import com.inductiveautomation.ignition.gateway.model.GatewayContext;
import org.example.api.script.ModelScriptAPI;
import org.example.http.JerseyConfig;
import org.example.http.JerseyIgnitionServlet;
import org.example.model.Model;
import org.example.service.ModelService;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class GatewayHook extends AbstractGatewayModuleHook {

    private static final LoggerEx logger = LoggerEx.newBuilder().build(GatewayHook.class);
    private static GatewayContext gatewayContext;
    private AnnotationConfigApplicationContext springContext;
    private AutowireCapableBeanFactory beanFactory;

    @Override
    public void setup(GatewayContext _gatewayContext) {
        logger.info("Spring Example is Starting Up");
        gatewayContext = _gatewayContext;
    }

    @Override
    public void startup(LicenseState licenseState) {


    }

    public void testSpringStuffWorks() {
        ModelScriptAPI modelScriptAPI = beanFactory.getBean(ModelScriptAPI.class);
        modelScriptAPI.test();

        ModelService modelService = beanFactory.getBean(ModelService.class);
        modelService.save(new Model("test"));
        modelService.save(new Model("test2"));
        modelService.getAll().forEach(model -> logger.info(model.getName()));
    }

    @Override
    public void shutdown() {
        logger.info("Spring Example is Shutting Down");
        gatewayContext.getWebResourceManager().removeServlet(JerseyConfig.SERVLET_ID);
        if (springContext != null) {
            springContext.close();
        }
    }

    public static GatewayContext getGatewayContext() {
        return gatewayContext;
    }

}

