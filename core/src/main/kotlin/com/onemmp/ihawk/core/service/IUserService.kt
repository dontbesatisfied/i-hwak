package com.onemmp.ihawk.core.service

import com.onemmp.ihawk.core.model.IUser
import com.onemmp.ihawk.core.model.Reference

interface IUserService {
    suspend fun createUser(name: String, email: String, password: String): IUser
    suspend fun getUser(email: String, password: String): IUser?
    suspend fun getUser(user: Reference<IUser>): IUser?
}