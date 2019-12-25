package integration.api

import com.ttreport.api.current.existing.ConsumerReleaseDocumentService
import com.ttreport.api.current.existing.DocumentAcceptanceService
import com.ttreport.api.current.existing.DocumentShipmentService
import com.ttreport.api.current.existing.EnterFPPDocumentService
import com.ttreport.api.current.existing.MarketEntranceDocumentService
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
