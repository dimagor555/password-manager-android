plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

group = "ru.dimagor555.core"
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

//                implementation(Libs.Koin.core)
                implementation(Libs.MviCompose.core)

                implementation(Libs.KotlinX.coroutinesCore)

                implementation(Libs.Decompose.decompose)
                implementation(Libs.Decompose.extensionsCompose)


            }
        }
        val androidMain by getting {
            dependencies {
//                implementation(Libs.AndroidX.securityCrypto)

//                implementation(Libs.Koin.core)
//                implementation(Libs.Koin.android)
            }
        }
        val desktopMain by getting {
            dependencies {

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
}
