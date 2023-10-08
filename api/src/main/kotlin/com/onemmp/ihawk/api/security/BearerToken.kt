package com.onemmp.ihawk.api.security

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.authority.AuthorityUtils

class BearerToken(private val token: String): AbstractAuthenticationToken(AuthorityUtils.NO_AUTHORITIES) {

    init {
        this.isAuthenticated = false
    }

    override fun getPrincipal(): String {
        return token
    }


    override fun getCredentials(): Any {
        return Unit
    }
}