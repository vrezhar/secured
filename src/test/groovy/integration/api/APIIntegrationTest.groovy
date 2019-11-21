package integration.api

import com.ttreport.api.current.ConsumerReleaseDocumentService
import com.ttreport.api.current.DocumentAcceptanceService
import com.ttreport.api.current.DocumentShipmentService
import com.ttreport.api.current.EnterFPPDocumentService
import com.ttreport.api.current.MarketEntranceDocumentService
import com.ttreport.api.resources.current.MarketEntranceCommand
import grails.testing.mixin.integration.Integration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.annotation.Rollback
import spock.lang.Specification

@Integration
@Rollback
class APIIntegrationTest extends Specification
{
    @Autowired ConsumerReleaseDocumentService consumerReleaseDocumentService
    @Autowired DocumentAcceptanceService documentAcceptanceService
    @Autowired DocumentShipmentService documentShipmentService
    @Autowired EnterFPPDocumentService enterFPPDocumentService
    @Autowired MarketEntranceDocumentService marketEntranceDocumentService
}
