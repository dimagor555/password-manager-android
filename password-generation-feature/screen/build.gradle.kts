plugins {
    id(Conventions.uiAndroidLibrary)
}

dependencies {
    implementation(projects.passwordGenerationFeature.domain)
    implementation(projects.uiCore)
    implementation(Libs.MviCompose.core)
    implementation(Libs.MviCompose.android)
}