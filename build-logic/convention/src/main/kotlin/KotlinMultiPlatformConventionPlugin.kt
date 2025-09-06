import com.android.build.api.dsl.LibraryExtension
import com.devper.app.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import com.devper.app.configureKotlinAndroid
import com.devper.app.configureKotlinMultiplatform

class KotlinMultiPlatformConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager){
            apply(libs.findPlugin("kotlinMultiplatform").get().get().pluginId)
            apply(libs.findPlugin("androidLibrary").get().get().pluginId)
            apply(libs.findPlugin("kotlin.serialization").get().get().pluginId)
            apply(libs.findPlugin("ktlint").get().get().pluginId)
        }

        extensions.configure<KotlinMultiplatformExtension>(::configureKotlinMultiplatform)
        extensions.configure<LibraryExtension>(::configureKotlinAndroid)
        extensions.configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
            debug.set(true)
        }
    }
}