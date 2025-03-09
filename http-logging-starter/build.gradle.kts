plugins {
    id("java-library")
    id("io.spring.dependency-management") version "1.1.3"
}

group = "org.example"
version = "1.0.0"

repositories {
    mavenCentral() // Добавляем Maven Central, иначе зависимости не подтянутся
}

dependencies {
    implementation("org.springframework.boot:spring-boot-autoconfigure:3.1.2")
    implementation("org.springframework.boot:spring-boot-starter-aop:3.1.2")
    implementation("org.slf4j:slf4j-api:2.0.7")
    implementation("org.aspectj:aspectjweaver:1.9.19")
    implementation("org.springframework.boot:spring-boot-starter-logging")

    testImplementation("org.springframework.boot:spring-boot-starter-test:3.1.2")
}

// Отключаем bootJar, т.к. модуль — это библиотека
tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar>().configureEach {
    enabled = false
}

tasks.withType<Jar>().configureEach {
    archiveClassifier.set("")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}