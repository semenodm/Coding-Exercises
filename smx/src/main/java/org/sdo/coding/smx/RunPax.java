package org.sdo.coding.smx;

import org.ops4j.pax.exam.Option;
import org.spockframework.runtime.extension.ExtensionAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created with IntelliJ IDEA.
 * User: dsemenov
 * Date: 7/25/13
 * Time: 9:18 PM
 * To change this template use File | Settings | File Templates.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ExtensionAnnotation(PaxExtension.class)
public @interface RunPax {
    String[] bundles() default {};
    String[] features() default {};
}
