package com.onemmp.ihawk.core.model

import com.devskiller.friendly_id.FriendlyId

interface Identity {
    val id: String
    val type: String

    companion object {
        fun generateId(): String {
            return FriendlyId.createFriendlyId()
        }
    }
}