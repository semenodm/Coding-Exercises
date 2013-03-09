package org.sdo.camel;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: dsemenov
 * Date: 3/9/13
 * Time: 1:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class MyOrderStrategy implements AggregationStrategy {

    Logger LOG = LoggerFactory.getLogger(this.getClass());

    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        // put order together in old exchange by adding the order from new exchange

        if (oldExchange == null) {
            // the first time we aggregate we only have the new exchange,
            // so we just return it
            return newExchange;
        }

        String orders = oldExchange.getIn().getBody(String.class);
        String newLine = newExchange.getIn().getBody(String.class);

        LOG.info("Aggregate old orders: " + orders);
        LOG.info("Aggregate new order: " + newLine);

        // put orders together separating by semi colon
        orders = orders + "\n" + newLine;
        // put combined order back on old to preserve it
        oldExchange.getIn().setBody(orders);

        // return old as this is the one that has all the orders gathered until now
        return oldExchange;
    }
}
