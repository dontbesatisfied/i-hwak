package com.onemmp.ihawk.core.model

import org.springframework.data.mongodb.core.mapping.Field
import java.util.*

class Password(
    override val sys: Sys,
    override val password: String
) : IPassword {

    constructor(password: String, user: Reference<out IUser>): this(Sys(user = user), password)

    data class Sys(
        @Field("id")
        override val id: String = Identity.generateId(),
        override val type: String = Password::class.simpleName!!,
        override val user: Reference<out IUser>,
        override val createdAt: Date,
        override val createdBy: Reference<out IUser>,
        override val updatedAt: Date,
        override val updatedBy: Reference<out IUser>,
    ): IPassword.Sys {

        constructor(id: String = Identity.generateId(), date: Date = Date(), user: Reference<out IUser>)
                : this(id, Password::class.simpleName!!, user, date, user, date, user)
    }
}