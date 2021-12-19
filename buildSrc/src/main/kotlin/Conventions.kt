import org.gradle.plugin.use.PluginDependenciesSpec

private const val androidLibraryPlugin = "com.android.library"

private const val kotlinLibraryConvention = "convention.kotlin-library"
private const val androidAppConvention = "convention.android-app"
private const val androidConvention = "convention.android"
private const val composeUiConvention = "convention.compose-ui"
private const val composeScreenConvention = "convention.compose-screen"

val PluginDependenciesSpec.`kotlin-library`
    get() = id(kotlinLibraryConvention)

val PluginDependenciesSpec.`android-app`
    get() = id(androidAppConvention)

val PluginDependenciesSpec.`android-library`
    get() = apply {
        id(androidLibraryPlugin)
        id(androidConvention)
    }

val PluginDependenciesSpec.`compose-ui-library`
    get() = apply {
        id(androidLibraryPlugin)
        id(androidConvention)
        id(composeUiConvention)
    }

val PluginDependenciesSpec.`compose-screen`
    get() = id(composeScreenConvention)