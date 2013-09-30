package org.sdo.coding.camel.words;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;
import org.apache.camel.test.junit4.CamelSpringTestSupport;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: dsemenov
 * Date: 8/14/13
 * Time: 5:05 PM
 */
public class WordsServiceIntegrationTest {//extends CamelSpringTestSupport {

   // @Test
    public void check_mq_connectivity() throws Exception {

        RouteBuilder routeBuilder = new RouteBuilder() {

            @Override
            public void configure() throws Exception {
                from("direct:entry").to("activemq:dictionary");
            }
        };

        DefaultCamelContext camelContext = new DefaultCamelContext();
        camelContext.addRoutes(routeBuilder);
        SimpleRegistry registry = new SimpleRegistry();
        camelContext.setRegistry(registry);
        camelContext.start();

        camelContext.createProducerTemplate().sendBody("direct:entry", "Hello world");
        //Thread.sleep(1000);
    }

    //@Override
    protected AbstractApplicationContext createApplicationContext() {
        return new ClassPathXmlApplicationContext("/META-INF/spring/words-processing.ctx.xml");
    }
}
