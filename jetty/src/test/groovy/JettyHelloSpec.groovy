import org.junit.Test;
import org.sdo.spock.jetty.JettySpeck;
import org.sdo.spock.jetty.RunJetty;

import spock.lang.Specification


@RunJetty
@RunRttp(services=["RDS", "LedgerNotification"])

class JettyHelloSpec extends JettySpeck{
			
	def "test hello"(){		
		expect:
		name.size() == length

		where:
		name     | length
		"Spock"  | 5
		"Kirk"   | 4
		"Scotty" | 6
	}

	def "test another hello"(){
		println "hello"
	}
}
