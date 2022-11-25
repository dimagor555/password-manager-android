plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

group = "ru.dimagor555.root"
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
                api(projects.passwordFeature)

                api(projects.passwordGenerationFeature)

                api(projects.masterPasswordFeature)

                implementation(projects.core)

                implementation(Libs.Koin.core)
                implementation(Libs.MviCompose.core)

                implementation(Libs.KotlinX.coroutinesCore)

                implementation(Libs.Decompose.decompose)
                implementation(Libs.Decompose.extensionsCompose)
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
