package com.onemmp.ihawk.api.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ServerWebExchange

@RestController
@RequestMapping("/")
class RootController {

    @GetMapping("/robots.txt")
    @ResponseStatus(HttpStatus.OK)
    suspend fun getRobots(): String {
        return "User-agent: *\nDisallow: /"
    }


    @GetMapping("/health")
    suspend fun getHealth(
        exchange: ServerWebExchange
    ) {
        exchange.response.statusCode = if (isReady) HttpStatus.OK else HttpStatus.BAD_GATEWAY
    }


    companion object {
        var isReady = false
    }
}