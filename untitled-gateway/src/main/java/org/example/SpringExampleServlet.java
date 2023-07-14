package org.example;

import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@Component
public class SpringExampleServlet extends DispatcherServlet {

    private static WebApplicationContext context;

    public static void setContext(WebApplicationContext _context) {
        context = _context;
    }

    public SpringExampleServlet() {
        super();
        setApplicationContext(context);
    }

}
