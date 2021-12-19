plugins {
    `kotlin-library`
}

dependencies {
    api(projects.core)
    api(projects.validation)

    api(Libs.KotlinX.datetime)
}