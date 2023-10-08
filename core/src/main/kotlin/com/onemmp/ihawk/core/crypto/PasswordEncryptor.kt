package com.onemmp.ihawk.core.crypto

class PasswordEncryptor(secret: String): SHA256(secret) {

}