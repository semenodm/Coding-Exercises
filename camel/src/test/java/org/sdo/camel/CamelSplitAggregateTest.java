package org.sdo.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dsemenov
 * Date: 3/9/13
 * Time: 12:51 PM
 * To change this template use File | Settings | File Templates.
 */
@ContextConfiguration(locations = "/split-aggregation.ctx.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class CamelSplitAggregateTest {
    @Autowired
    CamelContext context;

    @Produce(uri = "direct:input")
    protected ProducerTemplate template;

    @Before
    public void setup() throws Exception {
        context.getRouteDefinition("test_route").adviceWith(context, new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                interceptSendToEndpoint("file:./").skipSendToOriginalEndpoint().to("mock:advised");
            }
        });
    }

    @Test
    public void checkSplitAggregateRoute() throws InterruptedException {
        MockEndpoint mock = (MockEndpoint) context.getRouteDefinition("test_route").resolveEndpoint(context, "mock:advised");
        mock.expectedBodiesReceived("Dima,32.5\nKristina,16.0\nSonya,6.0\nLiza,3.5");
        List<MyBean> input = new ArrayList<MyBean>();
        input.add(new MyBean("Dima", 32.5));
        input.add(new MyBean("Kristina", 16.0));
        input.add(new MyBean("Sonya", 6.0));
        input.add(new MyBean("Liza", 3.5));
        template.sendBody(input);
        mock.assertIsSatisfied();
    }
}
