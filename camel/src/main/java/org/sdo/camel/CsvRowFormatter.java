package org.sdo.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Created with IntelliJ IDEA.
 * User: dsemenov
 * Date: 3/9/13
 * Time: 1:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class CsvRowFormatter implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        MyBean myBean = (MyBean) exchange.getIn().getBody();
        if (myBean != null) {
            StringBuilder csv = new StringBuilder();
            csv.append(myBean.getName()).append(',')
                    .append(myBean.getAge());
            exchange.getIn().setBody(csv.toString());
        }
    }
}
