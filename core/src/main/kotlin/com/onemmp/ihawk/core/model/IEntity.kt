package com.onemmp.ihawk.core.model

import java.util.Date

interface IEntity {

    val sys: Sys

    interface Sys: Identity {
        val createdAt: Date
        val createdBy: Reference<out IEntity>
        val updatedAt: Date
        val updatedBy: Reference<out IEntity>
    }
}