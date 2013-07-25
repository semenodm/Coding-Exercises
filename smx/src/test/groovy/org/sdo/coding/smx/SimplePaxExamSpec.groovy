package org.sdo.coding.smx

import spock.lang.Specification


/**
 * Created with IntelliJ IDEA.
 * User: dsemenov
 * Date: 7/25/13
 * Time: 9:02 PM
 * To change this template use File | Settings | File Templates.
 */
@RunPax
class SimplePaxExamSpec extends Specification {
    def "simple test"(){
        when:
            def two=2
        then:
            two +two == 5

    }
}