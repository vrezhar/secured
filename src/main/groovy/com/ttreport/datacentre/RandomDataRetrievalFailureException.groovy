package com.ttreport.datacentre

import com.ttreport.logs.DevCycleLogger

class RandomDataRetrievalFailureException extends Exception {
    String message
    RandomDataRetrievalFailureException(Exception e = null)
    {
        super(e)
        this.message = e?.getMessage() ?: "failed to retrieve random data"
    }
    @Override
    String getMessage()
    {
        return this.message
    }
    def log()
    {
        DevCycleLogger.log("message: ${this.message}")
        DevCycleLogger.log("stacktrace: ")
        this?.stackTrace?.each {
            DevCycleLogger.log(it.toString())
        }
    }
}
