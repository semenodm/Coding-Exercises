package org.sdo.spock.jetty;

import org.eclipse.jetty.server.Server;
import org.spockframework.runtime.AbstractRunListener;
import org.spockframework.runtime.extension.AbstractAnnotationDrivenExtension;
import org.spockframework.runtime.model.SpecInfo;

public class JettyExtention extends AbstractAnnotationDrivenExtension<RunJetty> {

	@Override
	public void visitSpecAnnotation(final RunJetty annotation, SpecInfo spec) {
		// TODO Auto-generated method stub
		spec.addListener(new AbstractRunListener() {
			Server server;

			@Override
			public void afterSpec(SpecInfo spec) {
				JettyUtils.stop(server);
			}

			@Override
			public void beforeSpec(SpecInfo spec) {
				server = JettyUtils.start(annotation.context(), annotation.webappDirectory(), annotation.port());
				super.beforeSpec(spec);
			}

		});
	}
}
