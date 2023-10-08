package com.onemmp.ihawk.core.model

import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.index.CompoundIndexes
import org.springframework.data.mongodb.core.mapping.Document

@Document("passwords")
@CompoundIndexes(
    CompoundIndex(def = "{'sys.id': 1}", unique = true, background = true),
    CompoundIndex(def = "{'sys.type': 1, 'sys.user.sys.id': 1}", background = true)
)
interface IPassword: IEntity {

    override val sys: Sys
    val password: String

    interface Sys: IEntity.Sys {
        val user: Reference<out IUser>
        override val createdBy: Reference<out IUser>
        override val updatedBy: Reference<out IUser>
    }
}