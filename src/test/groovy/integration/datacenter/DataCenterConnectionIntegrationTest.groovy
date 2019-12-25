package integration.datacenter

import com.ttreport.datacenter.MtisApiConnectorService
import grails.testing.mixin.integration.Integration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.annotation.Rollback
import spock.lang.Specification

@Integration
@Rollback
class DataCenterConnectionIntegrationTest extends Specification
{
    @Autowired
    MtisApiConnectorService dataCenterApiConnectorService

}
