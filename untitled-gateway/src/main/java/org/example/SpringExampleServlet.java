package org.example;

import com.inductiveautomation.ignition.common.util.LoggerEx;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

public class SpringExampleServlet extends DispatcherServlet {

    LoggerEx logger = LoggerEx.newBuilder().build(SpringExampleServlet.class);

    private static AnnotationConfigApplicationContext context;

    public static void setContext(AnnotationConfigApplicationContext _context) {
        context = _context;
    }

    public SpringExampleServlet() {
        super();

        if(context == null){
            logger.error("Context is null!");
        }
        setApplicationContext(context);
        //setContext(context);
        if(getServletContext() == null){
            logger.error("Servlet Context is null!");
        }
    }
}
