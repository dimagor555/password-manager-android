plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("com.android.library")
    id("dev.icerock.mobile.multiplatform-resources")
}

group = "ru.dimagor555.password"
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

                implementation(projects.core)
                implementation(projects.uiCore)
                implementation(projects.resCore)
                implementation(projects.validationCore)
                api(projects.encryptionCore)

                implementation(Libs.MviCompose.core)
                implementation(Libs.MviCompose.android)

                implementation(Libs.Decompose.decompose)

                implementation(Libs.Koin.core)
                implementation(Libs.Koin.compose)

                api(Libs.KotlinX.datetime)

                implementation(Libs.MokoResources.commonMain)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(Libs.MviCompose.core)
                implementation(Libs.MviCompose.android)

                implementation(Libs.Room.runtime)
                implementation(Libs.Room.ktx)

                implementation(Libs.MokoResources.androidMain)
            }
        }
        val desktopMain by getting {
            dependencies {
                api(compose.preview)

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
dependencies {
    implementation("androidx.appcompat:appcompat:1.4.1")
}

multiplatformResources {
    multiplatformResourcesPackage = "ru.dimagor555.password"
}