plugins {
    `kotlin-library`
}

dependencies {
    api(projects.core)
//    api(projects.validationCore)
    implementation(projects.validation.core)

    api(Libs.KotlinX.datetime)
}