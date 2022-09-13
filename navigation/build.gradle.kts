plugins {
    `compose-ui-library`
}

dependencies {
    implementation(projects.passwordFeature.ui)

    implementation(projects.passwordGenerationFeature.ui)

    implementation(projects.masterPasswordFeature.ui)

    implementation(Libs.Navigation.compose)
}