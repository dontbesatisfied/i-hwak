package com.onemmp.ihawk.api.controller

import com.onemmp.ihawk.core.exception.NotFound
import com.onemmp.ihawk.core.model.IUser
import com.onemmp.ihawk.core.service.IUserService
import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseCookie
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ServerWebExchange

@RestController
@RequestMapping("/auth")
/**
 * 클래스에 @Validated를 붙여주고, 유효성을 검증할 메소드의 파라미터에 @Valid를 붙여주면 유효성 검증이 진행된다.
 * @Validated를 클래스 레벨에 선언하면 해당 클래스에 유효성 검증을 위한 AOP의 어드바이스 또는 인터셉터(MethodValidationInterceptor)가 등록된다. 그리고 해당 클래스의 메소드들이 호출될 때 AOP의 포인트 컷으로써 요청을 가로채서 유효성 검증을 진행한다.
 * 이러한 이유로 @Validated를 사용하면 컨트롤러, 서비스, 레포지토리 등 계층에 무관하게 스프링 빈이라면 유효성 검증을 진행할 수 있다. 대신 클래스에는 유효성 검증 AOP가 적용되도록 @Validated를, 검증을 진행할 메소드에는 @Valid를 선언해주어야 한다.
 * 이러한 이유로 @Valid에 의한 예외는 MethodArgumentNotValidException이며, @Validated에 의한 예외는  ConstraintViolationException이다
 */
@Validated
class AuthController {

    @Autowired
    private lateinit var userService: IUserService

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun signUp(
        @RequestBody @Valid body: SignUpVO
    ): IUser {
        return userService.createUser(body.name, body.email, body.password)
    }


    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    suspend fun signIn(
        exchange: ServerWebExchange,
        @RequestBody @Valid body: SignInVO
    ) {
        userService.getUser(body.email, body.password) ?: throw NotFound()

        val cookie = ResponseCookie
            .from("accessToken", "accessToken")
            .httpOnly(true)
            .secure(true)
            .build()
        exchange.response.addCookie(cookie)
    }


    @PostMapping("/sign-out")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    suspend fun signOut() {
//        TODO: 구현
    }


    data class SignUpVO(
        /**
         * @Email
         * 어노테이션을 위 처럼 걸면 생성자의 파라미터에 설정되는것이고 실제로 우리가 원하는건 필드나 getter에 걸고 싶기때문에 field:를 명시해줘야 한다.
         * https://velog.io/@freddiey/Spring-validationwith-kotlin
         */
        @field:Email
        @field:NotBlank
        val email: String,
        @field:Size(min = 3, max = 32)
        val name: String,
        @field:Size(min = 8, max = 24)
        val password: String
    )


    data class SignInVO(
        @field:Email
        @field:NotBlank
        val email: String,
        @field:Size(min = 8, max = 24)
        val password: String
    )
}