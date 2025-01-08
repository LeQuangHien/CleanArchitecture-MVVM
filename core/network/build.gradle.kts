plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.cleanarchitecture.android.library)
}

android {
    namespace = "com.hienle.cleanarchitecture.core.network"

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {

    implementation(projects.core.model)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.bundles.arrow)
    implementation(libs.bundles.retrofit)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.koin)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}