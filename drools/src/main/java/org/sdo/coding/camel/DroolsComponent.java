package org.sdo.coding.camel;

import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;
import org.apache.camel.util.CamelContextHelper;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: dsemenov
 * Date: 11/23/13
 * Time: 3:45 PM
 */
public class DroolsComponent extends DefaultComponent {
    @Override
    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
        String kbaseBuilderRef = getAndRemoveParameter(parameters, "kbaseBuilder", String.class);
        DefaultDroolsDecisionTableBuilder kbaseBuilder = null;
        if (kbaseBuilderRef != null) {
           kbaseBuilder = CamelContextHelper.mandatoryLookup(getCamelContext(), kbaseBuilderRef, DefaultDroolsDecisionTableBuilder.class);
        }
        DroolsEndpoint endpoint = new DroolsEndpoint(uri, this, kbaseBuilder);
        return endpoint;
    }

}
