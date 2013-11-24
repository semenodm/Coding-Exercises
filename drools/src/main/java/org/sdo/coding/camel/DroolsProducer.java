package org.sdo.coding.camel;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;
import org.drools.KnowledgeBase;
import org.drools.runtime.StatelessKnowledgeSession;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dsemenov
 * Date: 11/23/13
 * Time: 3:58 PM
 */
public class DroolsProducer extends DefaultProducer {
    private final DefaultDroolsDecisionTableBuilder droolsDecisionTableBuilder;

    public DroolsProducer(Endpoint endpoint, DefaultDroolsDecisionTableBuilder decisionTableBuilder) {
        super(endpoint);
        droolsDecisionTableBuilder = decisionTableBuilder;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        KnowledgeBase knowledgeBase = droolsDecisionTableBuilder.buildKBase();

        StatelessKnowledgeSession statelessKnowledgeSession = knowledgeBase.newStatelessKnowledgeSession();

        statelessKnowledgeSession.execute(exchange.getIn().getBody(List.class));
    }
}
