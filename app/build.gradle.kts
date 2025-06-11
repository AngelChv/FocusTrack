import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    alias(libs.plugins.googleServices)
    alias(libs.plugins.firebase.crashlytics)

    alias(libs.plugins.hilt)
    // Hilt needs kapt (Kotlin Annotation Processing Tool) to generate the code
    kotlin("kapt")

    alias(libs.plugins.jetbrainsKotlinSerialization)
}

// Función para cargar la propiedad desde local.properties
fun getLocalProperty(key: String): String {
    val properties = Properties()
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        properties.load(FileInputStream(localPropertiesFile))
        return properties.getProperty(key, "")
    }
    return ""
}

android {
    // Production:
    //val tmdbAccessToken: String = project.findProperty("TMDB_ACCESS_TOKEN") as String? ?: ""

    // Dev:
    val tmdbAccessToken: String = getLocalProperty("TMDB_ACCESS_TOKEN")

    namespace = "io.github.angelchv.focustrack"
    compileSdk = 35

    defaultConfig {
        applicationId = "io.github.angelchv.focustrack"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "TMDB_ACCESS_TOKEN", "\"$tmdbAccessToken\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
}

dependencies {

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)

    implementation(libs.hilt)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    implementation(libs.androidx.ui.text.google.fonts)

    // Navigation Compose
    implementation(libs.androidx.navigation.compose)

    // JSON
    implementation(libs.kotlinx.serialization.json)

    // Auth
    implementation(libs.firebase.auth)

    // Android Credentials
    implementation(libs.androidx.credentials)
    implementation(libs.credentials.play.services.auth)

    implementation(libs.googleid)

    // Coil
    implementation(libs.coil)

    // Retrofit
    implementation(libs.retrofit2)
    implementation(libs.retrofit2.converter.gson)

    // OkHttp
    implementation(libs.okHttp)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}