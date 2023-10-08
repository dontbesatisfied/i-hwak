package com.onemmp.ihawk.api.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.web.server.context.ServerSecurityContextRepository
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class BearerSecurityContextRepository: ServerSecurityContextRepository {

    @Autowired
    private lateinit var reactiveAuthenticationManager: ReactiveAuthenticationManager

    override fun save(exchange: ServerWebExchange?, context: SecurityContext?): Mono<Void> {
        TODO("Not yet implemented")
    }

    override fun load(exchange: ServerWebExchange): Mono<SecurityContext> {
        val authHeader = exchange.request.headers.getFirst("Authorization") ?: return Mono.empty()

        if(!authHeader.startsWith("Bearer ")) { return Mono.empty() }

        val bearerToken = BearerToken(authHeader.substring("Bearer ".length))
//        val bearerToken = authHeader.substring("Bearer ".length)
        return reactiveAuthenticationManager.authenticate(bearerToken).map {
            SecurityContextImpl(it)
        }
    }
}