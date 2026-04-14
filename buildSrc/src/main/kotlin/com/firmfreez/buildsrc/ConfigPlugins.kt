
import ConfigPlugins.AndroidApplication
import ConfigPlugins.AndroidApplicationCompose
import ConfigPlugins.AndroidLibrary
import ConfigPlugins.AndroidLibraryCompose
import ConfigPlugins.KotlinLibrary
import ConfigPlugins.Protobuf

/**
 * This object contains constants related to the configuration plugins used in the project.
 *
 * Plugins are loads from the build-logic module, which is a custom Gradle plugin.
 *
 * @property AndroidApplicationCompose The plugin ID for the Android application with Compose support.
 * @property AndroidApplication The plugin ID for the Android application.
 * @property AndroidLibraryCompose The plugin ID for the Android library with Compose support.
 * @property AndroidLibrary The plugin ID for the Android library.
 * @property KotlinLibrary The plugin ID for the Kotlin library.
 * @property Protobuf The plugin ID for the Protobuf dependencies.
 */
object ConfigPlugins {

    const val AndroidApplicationCompose = "android.app.compose"
    const val AndroidApplication = "android.app"
    const val AndroidLibraryCompose = "android.library.compose"
    const val AndroidLibrary = "android.library"
    const val KotlinLibrary = "kotlin.library"
    const val Protobuf = "protobuf.dependencies"
}
