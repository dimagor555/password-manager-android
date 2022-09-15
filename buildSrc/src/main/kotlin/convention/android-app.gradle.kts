package convention

import Android
import Libs

plugins {
    id("com.android.application")
    id("convention.android")
    id("convention.compose-ui")
}

android {
    defaultConfig {
        applicationId = Android.appId
        targetSdk = Android.targetSdk
        versionName = Android.versionName
    }
    buildTypes {
        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }
}

dependencies {
    implementation(Libs.AndroidX.splashScreen)

    implementation(Libs.Navigation.compose)

    coreLibraryDesugaring(Libs.jdkDesugar)
}