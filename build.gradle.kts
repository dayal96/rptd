plugins {
    id("java-library")
}

group = "io.github.dayal96"
version = "1.0-SNAPSHOT"

tasks.withType<JavaCompile>() {
    options.release.set(17)
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
