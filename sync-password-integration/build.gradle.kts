plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
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
                implementation(projects.synchronizationFeature)
                implementation(projects.passwordFeature)

//                implementation(Libs.KotlinX.coroutinesCore)
//                implementation(Libs.KotlinX.datetime)
//
//                implementation(Libs.Ktor.clientCore)
//                implementation(Libs.Ktor.clientCio)
//                implementation(Libs.Ktor.clientLogging)
//                implementation(Libs.Ktor.clientSerialization)
//                implementation(Libs.Ktor.clientContentNegotiation)
//
//                implementation(Libs.Ktor.serialization)
////                implementation(Libs.Ktor.gson)
//
//                implementation(Libs.Ktor.serverCore)
//                implementation(Libs.Ktor.serverCio)
//                implementation(Libs.Ktor.serverContentNegotiation)
//                implementation(Libs.Ktor.serverCallLogging)
//
//                implementation(Libs.Slf4j.slf4jApi)
//                implementation(Libs.Slf4j.slf4jSimple)

                implementation(Libs.Koin.core)

                implementation(Libs.KotlinX.serialization)

                implementation(Libs.napier)
            }
        }
        val androidMain by getting {
            dependencies {
//                implementation(Libs.Ktor.clientAndroid)
            }
        }
        val jvmMain by getting {
            dependencies {

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
    packagingOptions {
        resources.excludes.add("META-INF/*")
        resources.excludes.add("META-INF/licenses/*")
        resources.excludes.add("**/attach_hotspot_windows.dll")
        resources.excludes.add("META-INF/io.netty.versions.properties")
    }
}