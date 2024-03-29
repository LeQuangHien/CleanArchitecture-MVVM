plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "com.hienle.cleanarchitecture.core.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

dependencies {

    implementation(projects.core.model)
    implementation(projects.core.network)
    implementation(projects.core.database)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    implementation(libs.koin)
    implementation(libs.bundles.arrow)

    testImplementation(libs.jupiter)
    testImplementation(libs.mockito)
    testImplementation(libs.mockk)
    testImplementation(libs.coroutines.test)
}
