package org.example.http;

import com.inductiveautomation.ignition.common.util.LoggerEx;
import org.glassfish.jersey.servlet.ServletContainer;

/**
 * Extended Jersey Servlet to pass in an AppConfig in the default constructor so that this works
 * with Ignition's servlet API
 * */
public class JerseyIgnitionServlet extends ServletContainer {

    LoggerEx logger = LoggerEx.newBuilder().build(JerseyIgnitionServlet.class);

    public JerseyIgnitionServlet(){
        super(new JerseyConfig());
        logger.info("Starting Jersey Servlet");
    }

}
