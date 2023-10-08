package com.onemmp.ihawk.core.exception

class UnprocessableContent(message: String = "This content is unable to process.", code: Codes = Codes.UnprocessableContent, details: Any? = null): Exception(message, code.toString(), details) {

    enum class Codes {
        UnprocessableContent,
        Conflict
    }
}