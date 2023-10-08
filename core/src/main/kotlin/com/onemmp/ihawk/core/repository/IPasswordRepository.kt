package com.onemmp.ihawk.core.repository

import com.onemmp.ihawk.core.model.IPassword
import com.onemmp.ihawk.core.model.IUser
import com.onemmp.ihawk.core.model.Reference

interface IPasswordRepository {
    suspend fun create(password: String, user: Reference<out IUser>): IPassword
    suspend fun findOneByUser(user: Reference<IUser>): IPassword?
}