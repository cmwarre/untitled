package org.example.api.rest;

import com.inductiveautomation.ignition.gateway.model.GatewayContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Component
public class ServletConfig extends WebMvcConfigurationSupport {

    @Autowired
    public ServletConfig(GatewayContext gatewayContext) {
        setServletContext(gatewayContext.getWebResourceManager().getServletContext());
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter());
    }

}
