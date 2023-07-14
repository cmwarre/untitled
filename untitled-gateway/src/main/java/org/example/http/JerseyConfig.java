package org.example.http;

import org.example.SpringConfig;
import org.example.api.rest.ModelResource;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spi.Container;
import org.glassfish.jersey.server.spi.ContainerLifecycleListener;
import org.glassfish.jersey.server.spring.SpringComponentProvider;
import org.glassfish.jersey.server.spring.SpringLifecycleListener;
import org.jvnet.hk2.spring.bridge.api.SpringBridge;
import org.jvnet.hk2.spring.bridge.api.SpringIntoHK2Bridge;
import org.jvnet.hk2.spring.bridge.internal.SpringBridgeImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("/system/example/api/*")
public class JerseyConfig extends ResourceConfig {

    public static final String SERVLET_ID = "example";

    private static AnnotationConfigApplicationContext springContext;

    public static void setSpringContext(AnnotationConfigApplicationContext _springContext) {
        springContext = _springContext;
    }

    public JerseyConfig() {

        register(new ContainerLifecycleListener() {
            @Override
            public void onStartup(Container container) {
                final var serviceLocator = container.getApplicationHandler()
                        .getInjectionManager()
                        .getInstance(ServiceLocator.class);

                SpringBridge.getSpringBridge().initializeSpringBridge(serviceLocator);
                final var springBridge = serviceLocator.getService(SpringIntoHK2Bridge.class);
                final var context = springContext;
                if (context == null) {
                    throw new RuntimeException("Spring Application Context not found");
                }
                springBridge.bridgeSpringBeanFactory(context.getBeanFactory());
            }

            @Override
            public void onReload(Container container) {

            }

            @Override
            public void onShutdown(Container container) {

            }
        });

        register(JacksonJaxbJsonProvider.class);
        register(SpringComponentProvider.class);

        register(ModelResource.class);


    }

}
