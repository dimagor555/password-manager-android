plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("com.android.library")
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
                implementation(projects.exportFeature.api)
                implementation(projects.core)
                implementation(projects.encryption.symmetric)
                implementation(projects.uiCore)
                implementation(projects.resCore)
                implementation(projects.backupFeature.api)

                implementation(Libs.KotlinX.serialization)
                implementation(Libs.KotlinX.datetime)

                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.preview)
                implementation(compose.ui)
                implementation(compose.materialIconsExtended)

                implementation(Libs.MviCompose.core)
                implementation(Libs.Decompose.decompose)

                implementation(Libs.Koin.core)

                implementation(Libs.napier)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(Libs.MviCompose.core)
                implementation(Libs.MviCompose.android)
                implementation(Libs.AndroidX.coreKtx)
                implementation(Libs.Compose.activity)
                implementation("androidx.compose.material:material:1.2.1")
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
    packagingOptions {
        resources.excludes.add("META-INF/*")
        resources.excludes.add("META-INF/licenses/*")
        resources.excludes.add("**/attach_hotspot_windows.dll")
        resources.excludes.add("META-INF/io.netty.versions.properties")
    }
}