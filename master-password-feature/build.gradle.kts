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
                implementation(compose.preview)
                implementation(compose.ui)
                implementation(compose.materialIconsExtended)

                implementation(projects.core)
                implementation(projects.uiCore)
                implementation(projects.resCore)
                implementation(projects.validationCore)
                implementation(projects.encryptionCore)

                implementation(Libs.MviCompose.core)
                implementation(Libs.MviCompose.android)

                implementation(Libs.Decompose.decompose)

                implementation(Libs.KotlinX.coroutinesCore)
                implementation(Libs.KotlinX.coroutinesSwing)

                implementation(Libs.settingsNoArg)

                implementation(Libs.Koin.core)
                implementation(Libs.Koin.compose)

                implementation(Libs.MokoResources.commonMain)

                implementation(Libs.napier)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(Libs.MviCompose.core)
                implementation(Libs.MviCompose.android)

                implementation(Libs.Argon2.android)

                implementation(Libs.AndroidX.biometric)

                implementation(Libs.MokoResources.androidMain)
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(compose.preview)

                implementation(Libs.MokoResources.jvmMain)

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

multiplatformResources {
    multiplatformResourcesPackage = "ru.dimagor555.masterpassword"
}
