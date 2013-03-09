package org.sdo.camel;

/**
 * Created with IntelliJ IDEA.
 * User: dsemenov
 * Date: 3/9/13
 * Time: 1:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class MyBean {
    private String name;
    private Double age;

    public String getName() {
        return name;
    }

    public Double getAge() {
        return age;
    }

    public MyBean(String name, Double age) {
        this.name = name;
        this.age = age;
    }

    public void setAge(Double age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }
}
