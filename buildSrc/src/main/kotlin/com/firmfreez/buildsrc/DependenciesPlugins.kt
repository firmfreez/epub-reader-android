
import DependenciesPlugins.AndroidCore
import DependenciesPlugins.ComposeUi

/** * This object contains constants related to the dependencies plugins used in the project.
 *
 * Dependencies plugins are loads from the build-logic module, which is a custom Gradle plugin.
 *
 * @property AndroidCore The plugin ID for the Android core dependencies.
 * @property ComposeUi The plugin ID for the Compose UI dependencies.
 */
object DependenciesPlugins {

    const val AndroidCore = "android.core.dependencies"
    const val ComposeUi = "android.compose.ui.dependencies"
    const val KoinFeature = "koin.feature.dependencies"
    const val KoinData = "koin.data.dependencies"
    const val KoinApp = "koin.app.dependencies"
    const val VoyagerUi = "voyager.dependencies"
}
