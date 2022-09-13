plugins {
    `compose-screen`
}

dependencies {
    implementation(projects.passwordGenerationFeature.domain)
    implementation(projects.uiCore)
}