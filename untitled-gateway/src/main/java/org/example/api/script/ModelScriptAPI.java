package org.example.api.script;

import com.inductiveautomation.ignition.common.util.LoggerEx;
import com.inductiveautomation.ignition.gateway.model.GatewayContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ModelScriptAPI {

    private static final LoggerEx logger = LoggerEx.newBuilder().build(ModelScriptAPI.class);

    private final GatewayContext gatewayContext;

    @Autowired
    public ModelScriptAPI(GatewayContext gatewayContext) {
        this.gatewayContext = gatewayContext;
    }

    public void test() {

        if(gatewayContext == null) {
            logger.warn("Gateway context is nulL!");
        } else {
            logger.info("Gateway context is not null!");
        }
    }

}
