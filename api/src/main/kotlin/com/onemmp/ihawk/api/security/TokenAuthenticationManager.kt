package com.onemmp.ihawk.api.security

import kotlinx.coroutines.reactor.mono
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class TokenAuthenticationManager: ReactiveAuthenticationManager {

    override fun authenticate(authentication: Authentication): Mono<Authentication> {
//        TODO:
        authentication.isAuthenticated = true
        return mono {
            authentication
        }
    }
}