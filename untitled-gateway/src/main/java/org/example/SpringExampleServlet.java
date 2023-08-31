package org.example;

import com.inductiveautomation.ignition.common.util.LoggerEx;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

public class SpringExampleServlet extends DispatcherServlet {

    LoggerEx logger = LoggerEx.newBuilder().build(SpringExampleServlet.class);

    public SpringExampleServlet() {
        super();
        setApplicationContext(GatewayHook.getSpringContext());
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

}
