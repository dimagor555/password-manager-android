import org.gradle.plugin.use.PluginDependenciesSpec

private const val kotlinLibraryConvention = "convention.kotlin-library"

val PluginDependenciesSpec.`kotlin-library`
    get() = id(kotlinLibraryConvention)