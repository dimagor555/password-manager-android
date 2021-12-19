package scripts

plugins {
    id("scripts.android-library")
}

android {
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose
    }
}

dependencies {
    implementation(Libs.AndroidX.lifecycleVmKtx)

    implementation(Libs.Compose.activity)
    implementation(Libs.Compose.ui)
    implementation(Libs.Compose.material)
    implementation(Libs.Compose.tooling)

    implementation(Libs.Navigation.compose)
    implementation(Libs.Navigation.hiltCompose)
}