package com.ttreport.datacentre

import grails.test.hibernate.HibernateSpec
import grails.testing.mixin.integration.Integration
import grails.testing.services.ServiceUnitTest
import grails.testing.spring.AutowiredTest
import org.springframework.test.annotation.Rollback

import static grails.async.Promises.*;
import grails.async.Promise;
import spock.lang.Specification

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
        service.retrieveToken()
        expect:
        true
    }
}
