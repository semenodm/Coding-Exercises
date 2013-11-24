package ord.sdo.coding.camel;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.sun.istack.internal.Nullable;
import org.drools.decisiontable.InputType;
import org.sdo.coding.camel.DefaultDroolsDecisionTableBuilder;
import org.sdo.coding.drools.validation.Holding;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.sdo.coding.drools.validation.HoldingType.*;

/**
 * Created with IntelliJ IDEA.
 * User: dsemenov
 * Date: 11/23/13
 * Time: 3:16 PM
 */
public class CamelDroolsComponentTest {

    @Test
    public void testDroolsEndpoint() throws Exception{
        RouteBuilder routeBuilder = new RouteBuilder() {

            @Override
            public void configure() throws Exception {
                from("direct:entry").to("drools:validation?kbaseBuilder=droolsDecisionMakerBuilder");
            }
        };

        DefaultCamelContext camelContext = new DefaultCamelContext();
        camelContext.addRoutes(routeBuilder);
        SimpleRegistry registry = new SimpleRegistry();
        DefaultDroolsDecisionTableBuilder tableBuilder = new DefaultDroolsDecisionTableBuilder();
        tableBuilder.setInputType(InputType.CSV);
        tableBuilder.setStartRow(10);
        tableBuilder.setStartCol(2);
        tableBuilder.setRulesPath("META-INF/org/sdo/coding/drools/validation/ValidationRules.csv");
        tableBuilder.setTemplatePath("META-INF/org/sdo/coding/drools/validation/ValidationRules.drt");
        registry.put("droolsDecisionMakerBuilder", tableBuilder);
        camelContext.setRegistry(registry);
        camelContext.start();

        List<Holding> holdings = Arrays.asList(
                new Holding("E_PRODUCT_CODE_1"),
                new Holding("CEF_PRODUCT_CODE_1"),
                new Holding(null, "manager2"),
                new Holding("MF_PRODUCT_CODE_1"),
                new Holding("E_PRODUCT_CODE_2"),
                new Holding(null, "manager1")
        );
        camelContext.createProducerTemplate().sendBody("direct:entry", holdings);


        assertThat(Iterables.find(holdings, new Predicate<Holding>() {
            @Override
            public boolean apply(@Nullable Holding holding) {
                return "E_PRODUCT_CODE_1".equals(holding.getProductCode());
            }
        }).getHoldingType(), is(EQUITY));
        assertThat(Iterables.find(holdings, new Predicate<Holding>() {
            @Override
            public boolean apply(@Nullable Holding holding) {
                return "E_PRODUCT_CODE_2".equals(holding.getProductCode());
            }
        }).getHoldingType(), is(EQUITY));

        assertThat(Iterables.find(holdings, new Predicate<Holding>() {
            @Override
            public boolean apply(@Nullable Holding holding) {
                return "CEF_PRODUCT_CODE_1".equals(holding.getProductCode());
            }
        }).getHoldingType(), is(CLOSED_END_FUND));

        assertThat(Iterables.find(holdings, new Predicate<Holding>() {
            @Override
            public boolean apply(@Nullable Holding holding) {
                return "MF_PRODUCT_CODE_1".equals(holding.getProductCode());
            }
        }).getHoldingType(), is(MUTUAL_FUND));

        assertThat(Iterables.find(holdings, new Predicate<Holding>() {
            @Override
            public boolean apply(@Nullable Holding holding) {
                return "manager1".equals(holding.getManagerId());
            }
        }).getHoldingType(), is(INVESTMENT_MANAGER));

        assertThat(Iterables.find(holdings, new Predicate<Holding>() {
            @Override
            public boolean apply(@Nullable Holding holding) {
                return "manager2".equals(holding.getManagerId());
            }
        }).getHoldingType(), is(INVESTMENT_MANAGER));
    }


}
