package com.onemmp.ihawk.core.model

import org.springframework.data.mongodb.core.mapping.Field
import java.util.*


class User(
    override val sys: Sys,
    override val name: String,
    override val email: String
): IUser {

    constructor(name: String, email: String) : this(Sys(), name, email)

    data class Sys(
        @Field("id")
        override val id: String = Identity.generateId(),
        override val type: String = User::class.simpleName!!,
        override val createdAt: Date = Date(),
        override val createdBy: Reference<out IUser>,
        override val updatedAt: Date = Date(),
        override val updatedBy: Reference<out IUser>,
        override val isAdmin: Boolean = false
    ): IUser.Sys {

        constructor(id: String = Identity.generateId(), date: Date = Date())
                : this(id, User::class.simpleName!!, date, Reference.of<User>(id), date, Reference.of<User>(id), false)
    }
}