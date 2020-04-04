plugins {
    java
    kotlin("jvm") version "1.3.61"
    id("com.github.johnrengelman.shadow") version "5.2.0" //Gradle plugin for collapsing all dependencies and project code into a single Jar file
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.amazonaws:aws-java-sdk-dynamodb:1.11.751")
    implementation("com.amazonaws:aws-lambda-java-events:2.2.6")
    implementation("com.amazonaws:aws-lambda-java-core:1.2.0")
    implementation("com.google.code.gson:gson:2.8.5")
//    implementation("com.fasterxml.jackson.core:jackson-core:2.10.3")
    implementation(kotlin("stdlib-jdk8"))
//    testCompile("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}