package org.sdo.spock.jetty

import spock.lang.Specification;

class JettySpeck extends Specification{

	def setup(){
		RunJetty runJetty = getRunJettyAnnotation()
		
	}
	
	protected RunJetty getRunJettyAnnotation(){
		RunJetty runJetty = this.class.getAnnotation(RunJetty)
		
		if(runJetty == null){
			runJetty = JettySpeck.getAnnotation(RunJetty.class)
		}
	}
}
