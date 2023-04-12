plugins {
    kotlin("multiplatform")
    id("com.android.library")
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
                implementation(Libs.Koin.core)
                implementation(Libs.KotlinX.coroutinesCore)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(Libs.Argon2.android)
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(Libs.Argon2.desktopSpringSecurity)
                implementation(Libs.Argon2.desktopBouncyCastle)
                implementation(Libs.Argon2.desktopCommonsLogging)
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
}
