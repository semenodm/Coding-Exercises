package org.sdo.spock.jetty;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.spockframework.runtime.extension.ExtensionAnnotation;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
@ExtensionAnnotation(JettyExtention.class)
public @interface RunJetty {
	String context() default "";
	int port() default 9090;
	String webappDirectory() default "src/main/webapp";
	String host() default "localhost";
}
