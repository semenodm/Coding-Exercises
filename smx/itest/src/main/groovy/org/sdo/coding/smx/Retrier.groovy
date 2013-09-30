package org.sdo.coding.smx

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Created with IntelliJ IDEA.
 * User: dsemenov
 * Date: 9/29/13
 * Time: 10:44 PM
 */
class Retrier {
    //static Logger log = LoggerFactory.getLogger(Retrier)
    static def retry (int times=5, long interval = 5000L, Closure errorHandler = {e,t -> println("fail on ${t} attempt")}, Closure task){
        try {
            if(times > 0) task.call()
        }   catch(e){
            errorHandler e, times
            sleep interval
            retry(times - 1, interval, errorHandler, task)
        }
    }
}
