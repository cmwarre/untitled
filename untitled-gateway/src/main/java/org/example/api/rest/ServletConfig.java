package org.example.api.rest;

import com.inductiveautomation.ignition.gateway.model.GatewayContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.PostConstruct;
import javax.servlet.FilterRegistration;
import java.util.List;
import java.util.Objects;

@Component
public class ServletConfig extends WebMvcConfigurationSupport {

    @Autowired
    public ServletConfig(GatewayContext gatewayContext) {
        var context = gatewayContext.getWebResourceManager().getServletContext();
        setServletContext(context);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter());
    }

    @PostConstruct
    public void configureSecurityFilters() {
        var context = getServletContext();
        Objects.requireNonNull(context, "Servlet context is null");
        FilterRegistration.Dynamic securityFilter = context.addFilter("securityFilterChain", new DelegatingFilterProxy("securityFilterChain"));
        securityFilter.addMappingForUrlPatterns(null, false, "/*");
    }

}
