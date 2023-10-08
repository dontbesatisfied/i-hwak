package com.onemmp.ihawk.core.repository

import com.onemmp.ihawk.core.crypto.PasswordEncryptor
import com.onemmp.ihawk.core.model.IPassword
import com.onemmp.ihawk.core.model.IUser
import com.onemmp.ihawk.core.model.Password
import com.onemmp.ihawk.core.model.Reference
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.findOne
import org.springframework.data.mongodb.core.insert
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class PasswordRepository: IPasswordRepository {
    @Autowired
    private lateinit var db: ReactiveMongoTemplate

    @Autowired
    private lateinit var passwordEncryptor: PasswordEncryptor

    override suspend fun create(password: String, user: Reference<out IUser>): IPassword {
        val encryptedPassword = passwordEncryptor.encrypt(password)
        return db.insert(Password(encryptedPassword, user)).awaitSingle()
    }

    override suspend fun findOneByUser(user: Reference<IUser>): IPassword? {
        return db.findOne(Query(Criteria.where("sys.type").`is`(Password::class.simpleName!!).and("sys.user.sys.id").`is`(user.sys.id)), Password::class.java).awaitSingleOrNull()
    }
}