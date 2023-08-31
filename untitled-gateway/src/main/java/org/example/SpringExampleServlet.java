package org.example;

import org.springframework.web.servlet.DispatcherServlet;

public class SpringExampleServlet extends DispatcherServlet {

    public SpringExampleServlet() {
        super();
        setApplicationContext(GatewayHook.getSpringContext());
    }

}
