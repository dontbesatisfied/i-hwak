package com.onemmp.ihawk.api

import com.onemmp.ihawk.api.controller.RootController
import com.onemmp.ihawk.core.config.security.SecurityProperty
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.event.ApplicationContextEvent
import org.springframework.context.event.EventListener

@SpringBootApplication
@ComponentScan("com.onemmp.ihawk")
// 패키지를 기반으로 @ConfigurationProperties가 등록된 클래스들을 찾아 값들을 주입하고 빈으로 등록해준다
@ConfigurationPropertiesScan("com.onemmp.ihawk")
class ApiApplication {

    @EventListener(ApplicationContextEvent::class)
    fun init() {
        RootController.isReady = true
    }
}

fun main(args: Array<String>) {
    runApplication<ApiApplication>(*args)
}
