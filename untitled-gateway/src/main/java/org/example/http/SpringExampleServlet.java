package org.example.http;

import org.example.GatewayHook;
import org.example.ServletConfig;
import org.example.SpringConfig;
import org.example.security.SecurityConfig;
import org.springframework.context.annotation.AutoProxyRegistrar;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class SpringExampleServlet extends DispatcherServlet {

    public SpringExampleServlet() {
        super();
        //setApplicationContext(GatewayHook.getSpringContext());

        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(SpringConfig.class, ServletConfig.class, SecurityConfig.class); // Register your configuration classes
        setApplicationContext(context);

        Thread.currentThread().setContextClassLoader(getClass().getClassLoader());

        // try to load the AutoProxyRegistrar class.
        try {
            Class.forName("org.springframework.context.annotation.AutoProxyRegistrar");
        } catch (ClassNotFoundException e) {
            logger.error("Failed to load AutoProxyRegistrar class", e);
        }

    }



}
