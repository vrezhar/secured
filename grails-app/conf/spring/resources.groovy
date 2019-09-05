package spring

import com.secured.auth.LinkBuiderService
import com.secured.auth.LinkEncoderService
import com.secured.auth.MailErrorHandlingService
import com.secured.auth.MailingService
import com.secured.auth.PatternValidatorService
import com.secured.auth.TokenGeneratorService
import com.secured.auth.UserInitializerService
import com.secured.auth.UserPasswordEncoderListener

import com.secured.auth.UserValidatorService
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy
// Place your Spring DSL code here
beans = {

    linkBuilderService(LinkBuiderService)

    linkEncoderService(LinkEncoderService)

    mailErrorHandlingService(MailErrorHandlingService)

    tokenGeneratorService(TokenGeneratorService)

    mailingService(MailingService)

    userInitializer(UserInitializerService)

    patternValidator(PatternValidatorService)

    userValidator(UserValidatorService)

    userPasswordEncoderListener(UserPasswordEncoderListener)

    sessionAuthenticationStrategy(NullAuthenticatedSessionStrategy)
}
