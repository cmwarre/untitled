package org.example.api.rest;

import org.example.GatewayHook;
import org.springframework.web.servlet.DispatcherServlet;

public class SpringExampleServlet extends DispatcherServlet {

    public SpringExampleServlet() {
        super();
        setApplicationContext(GatewayHook.getSpringContext());
    }

}
