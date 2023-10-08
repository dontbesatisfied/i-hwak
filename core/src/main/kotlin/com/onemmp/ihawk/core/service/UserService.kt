package com.onemmp.ihawk.core.service

import com.onemmp.ihawk.core.crypto.PasswordEncryptor
import com.onemmp.ihawk.core.exception.UnprocessableContent
import com.onemmp.ihawk.core.model.IUser
import com.onemmp.ihawk.core.model.Reference
import com.onemmp.ihawk.core.model.Reference.Companion.asReference
import com.onemmp.ihawk.core.model.User
import com.onemmp.ihawk.core.repository.IPasswordRepository
import com.onemmp.ihawk.core.repository.IUserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService: IUserService {

    @Autowired
    private lateinit var userRepository: IUserRepository

    @Autowired
    private lateinit var passwordRepository: IPasswordRepository

    @Autowired
    private lateinit var passwordEncryptor: PasswordEncryptor

    override suspend fun createUser(name: String, email: String, password: String): IUser {
        return if (userRepository.existsByEmail(email)) {
            throw UnprocessableContent("This email is already used.", UnprocessableContent.Codes.Conflict)
        } else {
            userRepository.create(name, email).also {
                passwordRepository.create(password, (it as User).asReference())
            }
        }
    }

    override suspend fun getUser(email: String, password: String): IUser? {
        val user = userRepository.findOneByEmail(email) ?: return null
        val pw = passwordRepository.findOneByUser((user as User).asReference()) ?: return null

        return if (passwordEncryptor.verify(password, pw.password)) user else null

    }

    override suspend fun getUser(user: Reference<IUser>): IUser? {
        return userRepository.findOneByReference(user)
    }
}