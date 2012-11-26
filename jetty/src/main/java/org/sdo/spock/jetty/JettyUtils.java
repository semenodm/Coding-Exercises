package org.sdo.spock.jetty;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.bio.SocketConnector;
import org.eclipse.jetty.webapp.WebAppContext;

public class JettyUtils {

	public static void stop(Server server) {
		try {
			server.stop();
		}
		catch (Exception e) {
			throw new RuntimeException("Couldn't stop server", e);
		}
	}

	public static Server start(String contextPath, String webappDirectory, int port) {
		Server server = new Server(port);
		SocketConnector socketConnector = createConnector(port);
		server.setConnectors(new Connector[] { socketConnector });
		WebAppContext context = createWebAppContext(webappDirectory, contextPath);
		server.setHandler(context);
		try {
			server.start();
		}
		catch (Exception e) {
			throw new RuntimeException("Couldn't run Jetty server", e);
		}
		return server;
	}

	private static WebAppContext createWebAppContext(String webappDirectory, String contextPath) {
		WebAppContext context = new WebAppContext();
		context.setWar(webappDirectory);
		context.setContextPath(contextPath);
		return context;
	}

	private static SocketConnector createConnector(int port) {
		SocketConnector connector = new SocketConnector();
		connector.setPort(port);

		return connector;
	}

}
