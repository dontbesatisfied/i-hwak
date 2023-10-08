package com.onemmp.ihawk.core.exception

class NotFound(message: String = "Server cannot find the requested resource.", code: Codes = Codes.ResourceNotFound, details: Any? = null): Exception(message, code.toString(), details) {

    enum class Codes {
        ResourceNotFound
    }
}