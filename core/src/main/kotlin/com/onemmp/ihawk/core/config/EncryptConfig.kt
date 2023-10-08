package com.onemmp.ihawk.core.config

import com.onemmp.ihawk.core.config.security.SecurityProperty
import com.onemmp.ihawk.core.crypto.EmailEncryptor
import com.onemmp.ihawk.core.crypto.PasswordEncryptor
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class EncryptConfig {

    @Bean
    @ConditionalOnMissingBean(EmailEncryptor::class)
    fun emailEncryptor(securityProperty: SecurityProperty): EmailEncryptor {
        return EmailEncryptor(securityProperty.encrypt.email.salt)
    }

    @Bean
    @ConditionalOnMissingBean(PasswordEncryptor::class)
    fun passwordEncryptor(securityProperty: SecurityProperty): PasswordEncryptor {
        return PasswordEncryptor(securityProperty.encrypt.password.salt)
    }
}