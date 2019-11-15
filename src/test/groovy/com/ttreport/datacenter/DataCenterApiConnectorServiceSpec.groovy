package com.ttreport.datacenter

import grails.test.hibernate.HibernateSpec
import grails.testing.services.ServiceUnitTest

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
        println(service.retrieveToken())
        expect:
        true
    }
}
