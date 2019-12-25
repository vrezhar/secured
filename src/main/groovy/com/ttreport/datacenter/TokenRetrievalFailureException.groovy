package com.ttreport.datacenter

import com.ttreport.logs.ServerLogger

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
        ServerLogger.log_exception(this)
        if(nested)
        {
            ServerLogger.log("nested exception is: ")
            nested.log()
        }
    }
}
