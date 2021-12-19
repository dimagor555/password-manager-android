plugins {
    id(Conventions.kotlinLibrary)
}

dependencies {
    api(projects.core)
    api(projects.validation)

    api(Libs.KotlinX.datetime)
}