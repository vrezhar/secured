package com.ttreport.datacentre

import grails.test.hibernate.HibernateSpec
import grails.testing.services.ServiceUnitTest

class DataCentreApiConnectorServiceSpec extends HibernateSpec implements  ServiceUnitTest<DataCentreApiConnectorService>{

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
