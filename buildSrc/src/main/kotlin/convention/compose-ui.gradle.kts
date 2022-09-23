package convention

androidCommon {
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.1"
    }
}

dependencies {
    implementation(Libs.Compose.activity)
    implementation(Libs.Compose.ui)
    implementation(Libs.Compose.tooling)
    implementation(Libs.Compose.material)
    implementation(Libs.Compose.iconsExtended)
}
