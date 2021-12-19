package convention

import Android
import Libs

plugins {
    id("com.android.application")
    id("convention.android")
    id("convention.compose-ui")
    id("dagger.hilt.android.plugin")
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

hilt {
    enableAggregatingTask = true
}

dependencies {
    implementation(Libs.Navigation.compose)

    coreLibraryDesugaring(Libs.jdkDesugar)
}