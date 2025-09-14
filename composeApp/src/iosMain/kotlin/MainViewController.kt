import androidx.compose.ui.window.ComposeUIViewController
import com.devper.app.App
import com.devper.app.di.appModule
import org.koin.core.context.startKoin
import platform.UIKit.UIViewController

fun mainViewController(): UIViewController =
    ComposeUIViewController(
        configure = {
            startKoin {
                modules(appModule)
            }
        },
    ) {
        App()
    }
