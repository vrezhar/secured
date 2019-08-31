package spring

import com.secured.auth.PatternValidatorService
import com.secured.auth.UserInitializerService
import com.secured.auth.UserPasswordEncoderListener
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy
// Place your Spring DSL code here
beans = {

    userInitializer(UserInitializerService)

    patternValidator(PatternValidatorService)

    userPasswordEncoderListener(UserPasswordEncoderListener)

    sessionAuthenticationStrategy(NullAuthenticatedSessionStrategy)
}
