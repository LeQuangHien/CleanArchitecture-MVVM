package com.hienle.cleanarchitecture.gradle.convention

import org.gradle.api.JavaVersion

object AppConfig {

    const val BUILD_TOOLS_VERSION = "34.0.0"
    const val COMPILE_VERSION = 35
    const val MIN_SDK = 29
    const val TARGET_SDK = 34
    val JAVA_VERSION = JavaVersion.VERSION_17
}