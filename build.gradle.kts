import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot") version "2.7.4"
    id("io.spring.dependency-management") version "1.0.14.RELEASE"
    java
    antlr
    idea
}
apply(plugin = "io.spring.dependency-management")

group = "io.github.dayal96"
version = "1.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

sourceSets {
    main {
        java {
            setSrcDirs(listOf("src/main/java", "build/generated-src/main/antlr"))
        }
    }

    test {
        java {
            setSrcDirs(listOf("src/test/java", "build/generated-src/main/antlr"))
        }
    }
}

idea.module {
    sourceDirs = sourceDirs + file("build/generated-src/main/antlr")
}

dependencies {
    implementation(files("library/bnl-lang.jar"))
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    antlr("org.antlr:antlr4:4.11.1")

    runtimeOnly("com.h2database:h2:2.1.214")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
}

tasks.generateGrammarSource {
    outputDirectory = File("${project
        .buildDir}/generated-src/main/antlr/io/github/dayal96/antlr")
    maxHeapSize = "128m"
    arguments.addAll(listOf("-package", "io.github.dayal96.antlr", "-visitor", "-no-listener"))
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
