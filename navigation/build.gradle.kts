plugins {
    `compose-ui-library`
}

dependencies {
    implementation(projects.passwordFeature.listScreen)
    implementation(projects.passwordFeature.detailsScreen)
    implementation(projects.passwordFeature.createScreen)
    implementation(projects.passwordFeature.editScreen)

    implementation(projects.passwordGenerationFeature.screen)

    implementation(projects.masterPasswordFeature.startScreen)
    implementation(projects.masterPasswordFeature.loginScreen)
    implementation(projects.masterPasswordFeature.editScreen)

    implementation(Libs.Navigation.compose)
}