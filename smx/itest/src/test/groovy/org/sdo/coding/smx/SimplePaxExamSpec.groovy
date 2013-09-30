package org.sdo.coding.smx

import spock.lang.Specification
import wslite.soap.SOAPClient


/**
 * Created with IntelliJ IDEA.
 * User: dsemenov
 * Date: 7/25/13
 * Time: 9:02 PM
 */
@RunPax
class SimplePaxExamSpec extends Specification {


    def "dictionary service test"() {
        setup: "prepare SOAP client for dictionary service"
        def client = new SOAPClient('http://localhost:8181/cxf/sdo/webservice/words/wordsServicePort')
        when: "execute translation service for word 'hello' from english to russian"
        def response = client.send(SOAPAction: 'http://words.components.coding.sdo.org/translateWord') {
            body {
                translateWordRequest() {
                    word("Hello")
                }
            }
        }
        then: "service should return 'привет' and 'здорова'"
        assert 200 == response.httpResponse.statusCode
        assert ['привет', 'здорова'] == response.translateWordResponse.translations.collect { it.text() }
    }
}