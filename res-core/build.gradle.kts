plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("dev.icerock.mobile.multiplatform-resources")
}

group = "ru.dimagor555.res.core"
version = "1.0"

kotlin {
    android()
    jvm("desktop") {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Libs.MokoResources.commonMain)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(Libs.MokoResources.androidMain)
            }
        }
        val desktopMain by getting {
            dependencies {
                implementation(Libs.MokoResources.jvmMain)
            }
        }
    }
}

android {
    compileSdk = 33
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 21
        targetSdk = 33
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
    multiplatformResourcesPackage = "ru.dimagor555.res.core"
}