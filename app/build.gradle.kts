plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id(Plugins.hilt)
}

android {
    compileSdk = Android.compileSdk

    defaultConfig {
        applicationId = Android.appId
        minSdk = Android.minSdk
        targetSdk = Android.targetSdk
        versionCode = Android.versionCode
        versionName = Android.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isDebuggable = false
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
        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

hilt {
    enableAggregatingTask = true
}

dependencies {
    implementation(projects.encryptionFeature.androidImpl)

    implementation(projects.passwordFeature.data)
    implementation(projects.passwordFeature.listScreen)
    implementation(projects.passwordFeature.detailsScreen)
    implementation(projects.passwordFeature.creationScreen)
    implementation(projects.passwordFeature.editingScreen)

    implementation(projects.passwordGenerationFeature.screen)

    implementation(projects.masterPasswordFeature.data)
    implementation(projects.masterPasswordFeature.hashing)
    implementation(projects.masterPasswordFeature.loginScreen)

    implementation(projects.uiCore)

    implementation(Libs.AndroidX.coreKtx)
    implementation(Libs.AndroidX.appCompat)
    implementation(Libs.AndroidX.lifecycleVmKtx)

    implementation(Libs.Compose.activity)
    implementation(Libs.Compose.ui)
    implementation(Libs.Compose.material)
    implementation(Libs.Compose.tooling)

    implementation(Libs.Navigation.compose)
    implementation(Libs.Navigation.hiltCompose)

    implementation(Libs.Hilt.android)
    kapt(Libs.Hilt.compiler)

    coreLibraryDesugaring(Libs.jdkDesugar)
}
