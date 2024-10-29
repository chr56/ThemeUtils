plugins {
    alias(libs.plugins.androidGradlePluginLibrary)
    alias(libs.plugins.kotlin.android)
}

val componentName = "stub"

android {

    compileSdk = 34
    buildToolsVersion = "34.0.0"

    namespace = "${rootProject.extra["libNamespacePrefix"]}.$componentName"


    defaultConfig {
        minSdk = 23
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        buildConfig = false
    }
}

dependencies {
    compileOnly(libs.androidx.core)
    compileOnly(libs.androidx.appcompat)
}

