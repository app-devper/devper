import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.devper.app.App
import com.devper.app.di.appModule
import org.koin.core.context.startKoin

fun main() {
    startKoin {
        modules(appModule)
    }
    return application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "DevperApp",
        ) {
            App()
        }
    }
}
