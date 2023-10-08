package com.onemmp.ihawk.core.config.security

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "ihawk.security")
data class SecurityProperty(
    val encrypt: EncryptProperty = EncryptProperty()
) {

    data class EncryptProperty(
        val email: SaltProperty = SaltProperty(),
        val password: SaltProperty = SaltProperty()
    )

    data class SaltProperty(
        val salt: String = "salt"
    )

}