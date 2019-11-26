package com.ttreport.datacenter

import com.ttreport.logs.DevCycleLogger

class TokenRetrievalFailureException extends Exception{
    RandomDataRetrievalFailureException nested
    String message
    TokenRetrievalFailureException(Exception e = null)
    {
        super(e)
        this.message = "failed to establish connection: ${super.getMessage()}"
    }
    TokenRetrievalFailureException(RandomDataRetrievalFailureException e)
    {
        super(e)
        nested = e
        this.message = "failed to retrieve token, corrupt random data"
    }
    @Override
    String getMessage()
    {
        return this.message
    }
    void log()
    {
        DevCycleLogger.log("message: ${this.message}")
        DevCycleLogger.log("stacktrace: ")
        DevCycleLogger.log_stack_trace(this)
        if(nested)
        {
            DevCycleLogger.log("nested exception is: ")
            nested.log()
        }
    }
}
