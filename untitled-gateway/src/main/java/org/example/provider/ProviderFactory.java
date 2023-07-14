package org.example.provider;

import com.inductiveautomation.ignition.common.util.LoggerEx;
import org.example.service.ModelService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ProviderFactory {

    private static final LoggerEx logger = LoggerEx.newBuilder().build(ProviderFactory.class);

    @EventListener
    public void handleConfigurationSaved(ModelService.ModelSavedEvent ae) {
        logger.warnf("Configuration saved for %s", ae.getSource().toString());
        logger.infof("After Config Received Thread ID " + Thread.currentThread().getId());
    }

}
