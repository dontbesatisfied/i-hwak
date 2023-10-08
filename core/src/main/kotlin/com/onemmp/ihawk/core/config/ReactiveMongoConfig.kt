package com.onemmp.ihawk.core.config

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory
import org.springframework.data.mongodb.config.EnableMongoAuditing
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper
import org.springframework.data.mongodb.core.convert.MappingMongoConverter
import org.springframework.data.mongodb.core.convert.MongoConverter
import org.springframework.data.mongodb.core.mapping.MongoMappingContext
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@Configuration
@EnableReactiveMongoAuditing
@EnableReactiveMongoRepositories
@ConditionalOnProperty(prefix = "spring.data.mongodb", name = ["uri"], matchIfMissing = false)
class ReactiveMongoConfig {

    @Bean
    @ConditionalOnMissingBean(ReactiveMongoTemplate::class)
    fun reactiveMongoTemplate(
        mongoDatabaseFactory: ReactiveMongoDatabaseFactory,
        mongoConverter: MongoConverter
    ): ReactiveMongoTemplate {
        return ReactiveMongoTemplate(mongoDatabaseFactory, mongoConverter)
    }

    @Bean
    @ConditionalOnMissingBean(MappingMongoConverter::class)
    fun mappingMongoConverter(
        mongoMappingContext: MongoMappingContext
    ): MappingMongoConverter {
        return MappingMongoConverter(ReactiveMongoTemplate.NO_OP_REF_RESOLVER, mongoMappingContext).apply {
            setTypeMapper(DefaultMongoTypeMapper(null)) // 다큐먼트에 _class 필드 제거.  https://madplay.github.io/post/how-to-remove-class-field-mongodb-in-spring-boot
        }
    }
}