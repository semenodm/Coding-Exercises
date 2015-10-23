package org.fix.benchmark;

import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.quickfixj.QuickfixjComponent;
import org.apache.camel.component.quickfixj.QuickfixjEndpoint;
import org.apache.camel.component.quickfixj.QuickfixjEventCategory;
import org.apache.camel.impl.DefaultCamelContext;
import org.openjdk.jmh.annotations.*;
import org.springframework.mock.jndi.SimpleNamingContext;
import quickfix.Session;
import quickfix.SessionID;
import quickfix.SessionStateListener;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Created by dsemenov
 * Date: 10/23/15.
 */
@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class FixEngineMicroBenchmark {
  public static void init() throws Exception {


  }

  @State(Scope.Thread)
  public static class SleepyState {
    public CamelContext context;
    public ProducerTemplate producerTemplate;
    public ConsumerTemplate consumerTemplate;

    @Setup
    public void setup() throws Exception {
      Semaphore s = new Semaphore(1);
      CamelContext camelContext = new DefaultCamelContext();

      camelContext.start();
      producerTemplate = camelContext.createProducerTemplate();
      consumerTemplate = camelContext.createConsumerTemplate();
      camelContext.addRoutes(new RouteBuilder() {
                               @Override
                               public void configure() throws Exception {
                                 from("direct:fix-input").to("quickfix:fix.cfg");
                                 from("quickfix:fix.cfg").filter(new Predicate() {
                                   @Override
                                   public boolean matches(Exchange exchange) {
                                     return exchange.getIn().getHeader(QuickfixjEndpoint.EVENT_CATEGORY_KEY).equals(QuickfixjEventCategory.AppMessageReceived);

                                   }
                                 }).to("direct:out");
//                                 .process(new Processor() {
//                                   @Override
//                                   public void process(Exchange exchange) throws Exception {
//                                     System.out.println("haha" + exchange);
//                                   }
//                                 });
                               }
                             }
      );

      final CountDownLatch latch = new CountDownLatch(1);

      Session session = Session.lookupSession(new SessionID("FIX.4.2:IN->OUT"));
      session.addStateListener(new SessionStateListener() {
        @Override
        public void onConnect() {

        }

        @Override
        public void onDisconnect() {

        }

        @Override
        public void onLogon() {
          latch.countDown();
        }

        @Override
        public void onLogout() {

        }

        @Override
        public void onReset() {

        }

        @Override
        public void onRefresh() {

        }

        @Override
        public void onMissedHeartBeat() {

        }

        @Override
        public void onHeartBeatTimeout() {

        }
      });
      latch.await();
      context = camelContext;
    }
  }

  @Benchmark
  public void measure_quickfix_throughput(SleepyState s) {
    s.producerTemplate.sendBody("direct:fix-input", "MASKED");
    Object o = s.consumerTemplate.receiveBody("direct:out");
        assert o != null;
    //System.out.println("haha" + o);
  }

}
