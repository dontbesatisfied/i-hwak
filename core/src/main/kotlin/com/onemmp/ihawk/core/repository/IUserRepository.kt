package com.onemmp.ihawk.core.repository

import com.onemmp.ihawk.core.model.IUser
import com.onemmp.ihawk.core.model.Reference

interface IUserRepository {
    suspend fun create(name: String, email: String): IUser
    suspend fun existsByEmail(email: String): Boolean
    suspend fun findOneByEmail(email: String): IUser?
    suspend fun findOneByReference(reference: Reference<IUser>): IUser?
}