plugins {
    id("com.android.application")
    kotlin("android")
    id("org.jetbrains.compose")
}

android {
    compileSdk = Android.compileSdk

    defaultConfig {
        applicationId = Android.appId
        minSdk = Android.minSdk
        targetSdk = Android.targetSdk
        versionCode = Android.versionCode
        versionName = Android.versionName

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            applicationIdSuffix = ".debug"
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    packagingOptions {
        resources.excludes.add("META-INF/*")
        resources.excludes.add("META-INF/licenses/*")
        resources.excludes.add("**/attach_hotspot_windows.dll")
        resources.excludes.add("META-INF/io.netty.versions.properties")
    }
}

dependencies {
    implementation(projects.encryptionCore)
    implementation(projects.passwordFeature)
    implementation(projects.masterPasswordFeature)
    implementation(projects.root)
    implementation(projects.uiCore)
    implementation(projects.synchronizationFeature)
    implementation(projects.syncPasswordIntegration)

    implementation(Libs.AndroidX.coreKtx)
    implementation(Libs.AndroidX.appCompat)
    implementation(Libs.AndroidX.splashScreen)
    implementation(Libs.Compose.activity)

    implementation(Libs.napier)

    implementation(Libs.Decompose.decompose)

    implementation(Libs.Koin.core)
    implementation(Libs.Koin.android)
    implementation(Libs.Koin.compose)

    implementation(Libs.Ktor.clientCore)
    implementation(Libs.Ktor.clientAndroid)
    implementation(Libs.Ktor.clientCio)

    implementation(Libs.Ktor.serverCore)
    implementation(Libs.Ktor.serverCio)
    implementation(Libs.Ktor.serverContentNegotiation)
}
