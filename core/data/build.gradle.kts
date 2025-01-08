plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.cleanarchitecture.android.library)
}

android {
    namespace = "com.hienle.cleanarchitecture.core.data"

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
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
