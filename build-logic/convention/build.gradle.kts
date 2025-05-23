import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.hienle.cleanarchitecture.buildlogic"

// Configure the build-logic plugins to target JDK 17
// This matches the JDK used to build the project, and is not related to what is running on device.
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    // register the convention plugin
    plugins {
        register("androidApplication") {
            id = "cleanarchitecture.android.application"
            implementationClass = "ApplicationConventionPlugin"
        }
    }
    plugins {
        register("androidLibrary") {
            id = "cleanarchitecture.android.library"
            implementationClass = "LibraryConventionPlugin"
        }
    }
}