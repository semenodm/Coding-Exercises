package org.sdo.algorythms.graph;

/**
 * Created with IntelliJ IDEA.
 * User: dsemenov
 * Date: 9/19/13
 * Time: 10:19 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Predicate<T> {
    boolean apply(T incoming);
}
