plugins {
    `compose-ui-library`
}

dependencies {
    api(projects.passwordFeature)

    api(projects.passwordGenerationFeature)

    api(projects.masterPasswordFeature)

    implementation(Libs.Navigation.compose)
}