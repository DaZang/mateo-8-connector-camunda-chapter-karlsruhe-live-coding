package io.camunda.connector;

import io.camunda.connector.api.annotation.OutboundConnector;
import io.camunda.connector.api.outbound.OutboundConnectorContext;
import io.camunda.connector.api.outbound.OutboundConnectorFunction;
import io.camunda.connector.service.MateoService;
import org.springframework.stereotype.Component;

@Component
@OutboundConnector(
        name = "MATEOCONNECTOR",
        inputVariables = {"skriptName"},
        type = "de.viadee:mateo:1"
)
public class MateoConnectorFunction implements OutboundConnectorFunction {

    MateoService mateoService;

    public MateoConnectorFunction (MateoService mateoService) {
        this.mateoService = mateoService;
    }

    @Override
    public Object execute(OutboundConnectorContext outboundConnectorContext) throws Exception {
        var connectorRequest = outboundConnectorContext.getVariablesAsType(MateoConnectorRequest.class);

        mateoService.executeScript(connectorRequest.getScriptName());

        MateoConnectorResult mateoConnectorResult = new MateoConnectorResult();
        mateoConnectorResult.setResult("Angemeldet");
        return mateoConnectorResult;
    }
}
