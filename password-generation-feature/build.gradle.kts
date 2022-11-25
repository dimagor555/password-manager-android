plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("com.android.library")
    id("dev.icerock.mobile.multiplatform-resources")
}

group = "ru.dimagor555.passwordgeneration"
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
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
                api(compose.preview)
                api(compose.ui)
                api(compose.materialIconsExtended)

                implementation(projects.uiCore)
                implementation(projects.resCore)

                implementation(Libs.MviCompose.core)

                implementation(Libs.Koin.core)
                implementation(Libs.Koin.compose)

                implementation(Libs.Decompose.decompose)

                implementation(Libs.MokoResources.commonMain)

                implementation(projects.core)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(Libs.MviCompose.core)
                implementation(Libs.MviCompose.android)

                implementation("androidx.compose.material:material:1.2.1")

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
    multiplatformResourcesPackage = "ru.dimagor555.passwordgeneration"
}