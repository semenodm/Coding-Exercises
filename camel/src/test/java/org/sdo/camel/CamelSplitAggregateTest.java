package org.sdo.camel;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.spi.RouteContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
//    @Autowired
//    RouteContext context;

    @Produce(uri = "direct:input")
    protected ProducerTemplate template;

    @Test
    public void checkSplitAggregateRoute() {
        List<MyBean> input = new ArrayList<MyBean>();
        input.add(new MyBean("Dima", 32.5));
        input.add(new MyBean("Kristina", 29.0));
        input.add(new MyBean("Sonya", 6.0));
        input.add(new MyBean("Liza", 3.5));
        template.sendBody(input);
    }
}
