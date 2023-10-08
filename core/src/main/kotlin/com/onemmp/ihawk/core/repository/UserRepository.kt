package com.onemmp.ihawk.core.repository

import com.onemmp.ihawk.core.crypto.EmailEncryptor
import com.onemmp.ihawk.core.model.IUser
import com.onemmp.ihawk.core.model.Reference
import com.onemmp.ihawk.core.model.User
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.exists
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class UserRepository: IUserRepository {
    @Autowired
    private lateinit var db: ReactiveMongoTemplate

    @Autowired
    private lateinit var emailEncryptor: EmailEncryptor

    override suspend fun create(name: String, email: String): IUser {
        val user = User(name, this.emailEncryptor.encrypt(email))
        return db.insert(user).awaitSingle()
    }

    override suspend fun existsByEmail(email: String): Boolean {
        val encryptedEmail = this.emailEncryptor.encrypt(email)
        return db.exists(
            Query(
                Criteria.where("sys.type").`is`(User::class.simpleName!!).and("email").`is`(encryptedEmail)
            ),
            User::class.java
        ).awaitSingle()
    }

    override suspend fun findOneByEmail(email: String): IUser? {
        val encryptedEmail = this.emailEncryptor.encrypt(email)
        return db.findOne(
            Query(
                Criteria.where("sys.type").`is`(User::class.simpleName!!).and("email").`is`(encryptedEmail)
            ),
            User::class.java
        ).awaitSingleOrNull()
    }

    override suspend fun findOneByReference(reference: Reference<IUser>): IUser? {
        return db.findOne(
            Query(
                Criteria.where("sys.type").`is`(User::class.simpleName!!).and("sys.id").`is`(reference.sys.id)
            ),
            User::class.java
        ).awaitSingleOrNull()
    }
}