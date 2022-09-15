plugins {
    `compose-ui-library`
}

dependencies {
    api(projects.passwordFeature.ui)

    api(projects.passwordGenerationFeature.ui)

    api(projects.masterPasswordFeature.ui)

    implementation(Libs.Navigation.compose)
}