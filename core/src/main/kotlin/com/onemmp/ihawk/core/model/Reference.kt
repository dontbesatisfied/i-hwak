package com.onemmp.ihawk.core.model

import org.springframework.data.mongodb.core.mapping.Field
import java.lang.reflect.Type
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.KTypeProjection
import kotlin.reflect.full.createType
import kotlin.reflect.typeOf

class Reference<T: IEntity>(
    val sys: Sys<T>
) {

    class Sys<K: IEntity>(
        @Field("id")
        override val id: String,
        val referType: String,
        override val type: String = Reference::class.simpleName!!
    ): Identity {

        constructor(id: String, referType: KClass<K>): this(id, referType.simpleName!!)

        companion object {
            inline fun <reified K: IEntity> of(id: String): Sys<K> = Sys(id, K::class)
        }
    }

    companion object {
        inline fun <reified T: IEntity> of(id: String): Reference<T> = Reference(Sys.of(id))
        inline fun <reified T: IEntity> T.asReference(): Reference<T> = Reference(Sys.of(this.sys.id))
//        inline fun <reified T: IEntity> T.asReference2(entityType: Type): Reference<T> {
//            return Reference<entityType.>(Sys.of(this.sys.id))
//        }
    }

}