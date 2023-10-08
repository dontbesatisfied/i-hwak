package com.onemmp.ihawk.api.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository
import org.springframework.security.web.server.context.ServerSecurityContextRepository
import reactor.core.publisher.Mono

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class SecurityConfiguration {

    @Bean
    fun webFilterChain(
        http: ServerHttpSecurity,
        authorizeExchangeSpec: Customizer<ServerHttpSecurity.AuthorizeExchangeSpec>,
        securityContextRepository: ServerSecurityContextRepository,
        reactiveAuthenticationManager: ReactiveAuthenticationManager,
    ): SecurityWebFilterChain {
        return http
            .exceptionHandling{
                it.authenticationEntryPoint { exchange, denied ->
                    Mono.fromRunnable {
                        exchange.response.statusCode = HttpStatus.UNAUTHORIZED
                    }
                }
                it.accessDeniedHandler { exchange, denied ->
                    Mono.fromRunnable {
                        exchange.response.statusCode = HttpStatus.FORBIDDEN
                    }
                }
            }
            .securityContextRepository(securityContextRepository)
            .authenticationManager(reactiveAuthenticationManager) // ServerSecurityContextRepository 가 없으면 동작하지 않는다.
            .authorizeExchange(authorizeExchangeSpec)
            .cors { it.disable() }
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .logout { it.disable() }
            .build()
    }

    @Bean
    fun authorizeExchangeSpec(): Customizer<ServerHttpSecurity.AuthorizeExchangeSpec> {
        return Customizer<ServerHttpSecurity.AuthorizeExchangeSpec> {
            it
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .pathMatchers("/health").permitAll()
                .pathMatchers("/robots.txt").permitAll()
                .pathMatchers("/auth/sign-in").permitAll()
                .pathMatchers("/auth/sign-up").permitAll()
                .pathMatchers("/actuator/**").permitAll()
                .anyExchange().authenticated()
        }
    }
}