import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.4"
    id("io.spring.dependency-management") version "1.1.3"
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
    kotlin("kapt") version "1.8.21"
}

group = "com.onemmp.ihawk"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

//kapt {
//    annotationProcessor("org.springframework.boot.configurationprocessor.ConfigurationMetadataAnnotationProcessor")
//}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":core"))

//    for property
//    https://perfectacle.github.io/2021/11/21/spring-boot-configuration-processor/
//    https://docs.spring.io/spring-boot/docs/current/reference/html/configuration-metadata.html#appendix.configuration-metadata.annotation-processor
//    kapt("org.springframework.boot:spring-boot-configuration-processor")
//    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")

    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")

//    Gradle의 developmentOnly는 개발환경에만 적용되는 설정이다. 즉, 운영환경에 배포되는 jar, war 파일에는 developmentOnly로 설치된 라이브러리는 제외된다.
    developmentOnly("org.springframework.boot:spring-boot-starter-actuator")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
