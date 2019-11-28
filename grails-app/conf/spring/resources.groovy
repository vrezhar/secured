package spring

import com.ttreport.api.current.EnterFPPDocumentService
import com.ttreport.api.deprecated.BarCodeService
import com.ttreport.api.deprecated.CompanyService
import com.ttreport.api.current.ValidationErrorResolverService
import com.ttreport.mail.MailErrorHandlingService
import com.ttreport.mail.MailingService
import com.ttreport.auth.PatternValidatorService
import com.ttreport.auth.UserInitializerService
import com.ttreport.auth.UserPasswordEncoderListener
import com.ttreport.auth.UserValidatorService
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy
// Place your Spring DSL code here
beans = {

//    productsService(ProductsService)
//
//    documentService(DocumentService)

    validationErrorResolverService(ValidationErrorResolverService)

    companyService(CompanyService)

    barCodeService(BarCodeService)

    mailErrorHandlingService(MailErrorHandlingService)

    mailingService(MailingService)

    userInitializer(UserInitializerService)

    patternValidator(PatternValidatorService)

    userValidator(UserValidatorService)

    userPasswordEncoderListener(UserPasswordEncoderListener)

    sessionAuthenticationStrategy(NullAuthenticatedSessionStrategy)
}

