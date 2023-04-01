plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("com.android.library")
    id("dev.icerock.mobile.multiplatform-resources")
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
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)

                implementation(projects.resCore)

                implementation(Libs.MokoResources.commonMain)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(Libs.Koin.core)
                implementation(Libs.Koin.compose)

                implementation(Libs.MokoResources.androidMain)
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(compose.preview)

                implementation(Libs.MokoResources.jvmMain)
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

multiplatformResources {
    multiplatformResourcesPackage = "ru.dimagor555.password.validation"
}