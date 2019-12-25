package com.ttreport.datacenter

import com.ttreport.logs.ServerLogger

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
    void log()
    {
        ServerLogger.log_exception(this)
    }
}
