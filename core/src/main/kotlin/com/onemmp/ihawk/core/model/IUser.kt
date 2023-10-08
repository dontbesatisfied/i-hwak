package com.onemmp.ihawk.core.model

import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.index.CompoundIndexes
import org.springframework.data.mongodb.core.mapping.Document

@Document("users")
@CompoundIndexes(
    CompoundIndex(def = "{'sys.id': 1}", unique = true, background = true),
    CompoundIndex(def = "{'sys.type': 1, 'email': 1}", partialFilter = "{'sys.type': 'User'}", unique = true, background = true)
)
interface IUser: IEntity {
    override val sys: Sys
    val name: String
    val email: String


    interface Sys: IEntity.Sys {
        override val createdBy: Reference<out IUser>
        override val updatedBy: Reference<out IUser>
        val isAdmin: Boolean
    }
}