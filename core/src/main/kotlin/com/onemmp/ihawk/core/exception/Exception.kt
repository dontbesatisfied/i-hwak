package com.onemmp.ihawk.core.exception

open class Exception(
    message: String,
    val code: String,
    val details: Any?
): RuntimeException(message) {

}