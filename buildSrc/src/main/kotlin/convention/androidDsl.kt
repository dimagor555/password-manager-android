package convention

import com.android.build.api.dsl.*
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.ExtensionAware
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

private typealias AndroidCommonExtension = CommonExtension<BuildFeatures, BuildType, DefaultConfig, ProductFlavor>

internal fun Project.androidCommon(configure: Action<AndroidCommonExtension>) =
    (this as ExtensionAware).extensions.configure("android", configure)

internal fun AndroidCommonExtension.kotlinOptions(configure: Action<KotlinJvmOptions>) =
    (this as ExtensionAware).extensions.configure("kotlinOptions", configure)

internal fun DependencyHandler.implementation(dependencyNotation: Any) =
    add("implementation", dependencyNotation)

internal fun DependencyHandler.kapt(dependencyNotation: Any) =
    add("kapt", dependencyNotation)