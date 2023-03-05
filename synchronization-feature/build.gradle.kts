plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("org.jetbrains.compose")
    id("com.android.library")
//    id("dev.icerock.mobile.multiplatform-resources")
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
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
                api(compose.preview)
                api(compose.ui)
                api(compose.materialIconsExtended)

                implementation(projects.core)
                implementation(projects.uiCore)
                implementation(projects.resCore)

                implementation(Libs.KotlinX.coroutinesCore)
                implementation(Libs.KotlinX.datetime)

                implementation(Libs.Ktor.clientCore)
                implementation(Libs.Ktor.clientCio)
                implementation(Libs.Ktor.clientLogging)
                implementation(Libs.Ktor.clientSerialization)
                implementation(Libs.Ktor.clientContentNegotiation)

                implementation(Libs.Ktor.serialization)

                implementation(Libs.Ktor.serverCore)
                implementation(Libs.Ktor.serverCio)
                implementation(Libs.Ktor.serverContentNegotiation)
                implementation(Libs.Ktor.serverCallLogging)

                implementation(Libs.Slf4j.slf4jApi)
                implementation(Libs.Slf4j.slf4jSimple)

                implementation(Libs.Koin.core)

                implementation(Libs.KotlinX.serialization)

                implementation(Libs.MviCompose.core)
                implementation(Libs.MviCompose.android)

                implementation(Libs.Decompose.decompose)

                implementation(Libs.MokoResources.commonMain)

                implementation(Libs.napier)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(Libs.Ktor.clientAndroid)

                implementation(Libs.MokoResources.androidMain)
            }
        }
        val jvmMain by getting {
            dependencies {
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
    packagingOptions {
        resources.excludes.add("META-INF/*")
        resources.excludes.add("META-INF/licenses/*")
        resources.excludes.add("**/attach_hotspot_windows.dll")
        resources.excludes.add("META-INF/io.netty.versions.properties")
    }
}