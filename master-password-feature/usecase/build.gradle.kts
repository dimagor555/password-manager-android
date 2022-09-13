plugins {
    `kotlin-library`
}

dependencies {
    api(projects.masterPasswordFeature.domain)
//    api(projects.validationCore)
    implementation(projects.validation.core)
    implementation(Libs.KotlinX.coroutinesCore)
}