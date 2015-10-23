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
  @State(Scope.Thread)
  public static class SleepyState {
    public CamelContext context;
    public ProducerTemplate producerTemplate;
    public ConsumerTemplate consumerTemplate;

    @Setup
    public void setup() throws Exception {
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

    @Setup(Level.Invocation)
    public void sendMessage() {
      producerTemplate.sendBody("direct:fix-input", "8=FIX.4.2\u00019=200\u000135=D\u000134=1084\u000149=IN\u000152=20140922-13:53:07.774\u000156=OUT\u00011=JX-E0020-1-CF\u000111=AAA-0002-09/22/14\u000112=0.21\u000113=2\u000121=2\u000138=11\u000140=1\u000154=1\u000155=3866G7\u000158=ACA-0002-09/22/14\u000159=0\u000160=20140922-13:53:05.401\u000163=0\u000110=4\u0001");
    }
  }

  @Benchmark
  public void measure_quickfix_throughput(SleepyState s) {
    Object o = s.consumerTemplate.receiveBody("direct:out");
    assert o != null;
  }

}
