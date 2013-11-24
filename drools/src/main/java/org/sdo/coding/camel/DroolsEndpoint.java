package org.sdo.coding.camel;

import org.apache.camel.*;
import org.apache.camel.impl.DefaultEndpoint;

/**
 * Created with IntelliJ IDEA.
 * User: dsemenov
 * Date: 11/23/13
 * Time: 3:53 PM
 */
public class DroolsEndpoint extends DefaultEndpoint {

    private DefaultDroolsDecisionTableBuilder decisionTableBuilder;

    public DroolsEndpoint(String endpointUri, Component component, DefaultDroolsDecisionTableBuilder decisionTableBuilder) {
        super(endpointUri, component);
        this.decisionTableBuilder = decisionTableBuilder;
    }

    @Override
    public Producer createProducer() throws Exception {
        return new DroolsProducer(this, decisionTableBuilder);
    }

    @Override
    public Consumer createConsumer(Processor processor) throws Exception {
        throw new IllegalAccessException("drools component cannot act as consumer");
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
