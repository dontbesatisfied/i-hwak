package com.onemmp.ihawk.api.exception

import com.onemmp.ihawk.core.exception.NotFound
import com.onemmp.ihawk.core.exception.UnprocessableContent
import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.support.WebExchangeBindException
import org.springframework.web.server.ServerWebExchange


/**
 * ControllerAdvice는 여러 컨트롤러에 대해 전역적으로 ExceptionHandler를 적용해준다.
 */
@RestControllerAdvice
class AppAdvice {

    @ExceptionHandler(UnprocessableContent::class)
    fun handleException(exchange: ServerWebExchange, e: UnprocessableContent): ResponseEntity<ErrorResponse> {
        /**
         * org.springframework.http.ResponseEntity is from the original Spring Mvc Framework package and org.springframework.web.reactive.function.server.ServerResponse is from the spring Reactive package.
         * 둘의 차이는 ServerResponse는 Filter 단에서 AbstractErrorWebExceptionHandler을 상속받은 구현체가 동작하는 것이고, ResponseEntity는 Contoller Datatype의 하나로 ControllerAdvice에서 구현시 지원하는 data type으로 DispatcherServlet 내부에서 동작합니다.
         * Annotated Controllers 에서는 ResponseEntity 사용해야한다. https://docs.spring.io/spring-framework/reference/web/webflux/controller/ann-methods/return-types.html
         */
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ErrorResponse(e.message!!, e.code, e.details))
    }

    @ExceptionHandler(NotFound::class)
    fun handleException(exchange: ServerWebExchange, e: NotFound): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse(e.message!!, e.code, e.details))
    }

    /**
     * validation error
     */
    @ExceptionHandler(WebExchangeBindException::class)
    fun handleException(exchange: ServerWebExchange, e: WebExchangeBindException): ResponseEntity<ErrorResponse> {
        val details = e.bindingResult.fieldErrors.map {
            mapOf(
                "field" to it.field,
                "value" to it.rejectedValue,
                "message" to it.defaultMessage
            )
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse("Invalid parameter", "ValidationError", details))
    }

//    @ExceptionHandler(ConstraintViolationException::class)
//    fun handleException(exchange: ServerWebExchange, e: ConstraintViolationException): ResponseEntity<ErrorResponse> {
//        val details = HashMap<String, String>()
//        e.constraintViolations.forEach {
//            details[it.propertyPath.last().name] = it.message
//        }
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse("Invalid parameter", "ValidationError", details))
//    }
}