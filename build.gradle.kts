import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot") version "2.7.4"
    id("io.spring.dependency-management") version "1.0.14.RELEASE"
    id("java")
    antlr
}
apply(plugin = "io.spring.dependency-management")

group = "io.github.dayal96"
version = "1.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation(files("library/bnl-lang.jar"))
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    runtimeOnly("org.antlr:antlr4:4.10.1")
    runtimeOnly("com.h2database:h2:2.1.214")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.generateGrammarSource {
    outputDirectory = File("${project
        .buildDir}/generated-src/antlr/main/io/github/dayal96/antlr")
    maxHeapSize = "128m"
    arguments = arguments + listOf("-package", "io.github.dayal96.antlr", "-visitor",
        "-no-listener")
}

tasks.named<BootJar>("bootJar") {
    mainClass.set("io.github.dayal96.Application");
}

tasks.jar {
    dependsOn("bootJar")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
