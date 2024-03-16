plugins {
    alias(libs.plugins.androidGradlePluginLibrary)
    alias(libs.plugins.kotlin.android)
    id("maven-publish")
}

val componentName = "tint"

android {

    compileSdk = 34
    buildToolsVersion = "34.0.0"

    namespace = "${rootProject.extra["libNamespacePrefix"]}.$componentName"


    defaultConfig {
        minSdk = 23

        consumerProguardFiles.add(File("consumer-rules.pro"))

        aarMetadata {
            minCompileSdk = 23
        }
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = false
            proguardFiles.apply {
                add(getDefaultProguardFile("proguard-android-optimize.txt"))
                add(File("proguard-rules.pro"))
            }
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

    publishing {
        publishing {
            singleVariant("release") {
                withSourcesJar()
            }
        }
    }

}

dependencies {
    api(projects.libColor)

    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    compileOnly(libs.androidx.constraintlayout)
    compileOnly(libs.google.material)
}

publishing {
    publications {
        create<MavenPublication>("release") {
            afterEvaluate {
                from(components["release"])
            }
            version = rootProject.extra["libVersion"] as String
            groupId = rootProject.extra["libGroup"] as String
            artifactId = "$componentName-util"
        }
    }
}
