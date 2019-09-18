package spring

import com.secured.api.BarCodeService
import com.secured.api.CompanyService
import com.secured.mail.MailErrorHandlingService
import com.secured.mail.MailingService
import com.secured.auth.PatternValidatorService
import com.secured.auth.UserInitializerService
import com.secured.auth.UserPasswordEncoderListener
import com.secured.auth.UserValidatorService
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy
// Place your Spring DSL code here
beans = {


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
