plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("com.android.library")
    id("io.realm.kotlin")
    kotlin("plugin.serialization")
}

kotlin {
    android()
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.core)
                implementation(projects.uiCore)
                implementation(projects.resCore)
                implementation(projects.validationCore)
                implementation(projects.encryptionCore)

                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.preview)
                implementation(compose.ui)
                implementation(compose.materialIconsExtended)

                implementation(Libs.MviCompose.core)
                implementation(Libs.MviCompose.android)
                implementation(Libs.Decompose.decompose)

                implementation(Libs.Koin.core)
                implementation(Libs.Koin.android)

                implementation(Libs.KotlinX.datetime)
                implementation(Libs.KotlinX.serialization)

                implementation(Libs.Realm.base)

                implementation(Libs.napier)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(Libs.MviCompose.core)
                implementation(Libs.MviCompose.android)
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(compose.preview)
            }
        }
    }
}

android {
    compileSdk = Android.compileSdk
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = Android.minSdk
        targetSdk = Android.targetSdk
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.1"
    }
}