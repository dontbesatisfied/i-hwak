package com.onemmp.ihawk.api.controller

import com.onemmp.ihawk.core.exception.NotFound
import com.onemmp.ihawk.core.model.IUser
import com.onemmp.ihawk.core.model.Reference
import com.onemmp.ihawk.core.service.IUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ServerWebExchange

@RestController
@RequestMapping("/users")
class UserController {

    @Autowired
    private lateinit var userService: IUserService

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun getUser(
        exchange: ServerWebExchange,
        @PathVariable userId: String
    ): IUser {
        return userService.getUser(Reference.of(userId)) ?: throw NotFound()
    }
}