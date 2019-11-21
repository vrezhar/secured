package com.ttreport.datacenter

import grails.test.hibernate.HibernateSpec
import grails.testing.services.ServiceUnitTest
import static grails.async.Promises.task
import static grails.async.Promises.waitAll

class DataCenterApiConnectorServiceSpec extends HibernateSpec implements  ServiceUnitTest<DataCenterApiConnectorService>{

    List<Class> getDomainClasses()
    {
        []
    }


    def setup(){
        assert service != null
    }

    void "test api connection"()
    {
        String token = waitAll(task{
            service.retrieveToken()
        })
        println(new Date().toInstant().epochSecond)
        println(service.parseToken(token).exp)
        expect:
        !service.isExpired(token)
    }
}
