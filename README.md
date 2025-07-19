# Project Structure

This project uses a modular, multiplatform architecture with clear separation of concerns. Below is an overview of the main directories and their roles:

```
├── build-logic/           # Gradle build logic and conventions
├── composeApp/            # Main Compose Multiplatform application
│   ├── src/
│   │   ├── androidMain/   # Android-specific code
│   │   ├── commonMain/    # Shared code across platforms
│   │   ├── desktopMain/   # Desktop-specific code
│   │   ├── iosMain/       # iOS-specific code
│   │   └── wasmJsMain/    # WebAssembly JS-specific code
│   └── build.gradle.kts
├── core/
│   ├── common/            # Common utilities and base classes
│   ├── data/              # Data layer (repositories, sources)
│   ├── design/            # Design system (UI components, themes)
│   └── domain/            # Domain layer (business logic, models)
├── feature/
│   ├── login/             # Login feature module
│   └── main/              # Main feature module
├── shared/                # Shared code and resources
├── server/                # Backend server code
├── iosApp/                # Native iOS app
├── kotlin-js-store/       # JS/WASM build artifacts
├── gradle/                # Gradle configuration files
├── build.gradle.kts       # Root Gradle build file
├── settings.gradle.kts    # Gradle settings
└── README.md              # Project documentation
```

## Design Patterns

- **Modular Architecture**: Code is organized into core, feature, and shared modules for scalability and maintainability.
- **MVVM (Model-View-ViewModel)**: Used in UI layers (Compose, iOS) to separate presentation logic from business logic.
- **Repository Pattern**: Data access is abstracted via repositories in the `core/data` module.
- **Dependency Injection**: Promotes loose coupling between modules (can use Koin, Dagger, or manual DI).
- **Multiplatform Shared Code**: Common business logic and models are placed in `commonMain` for reuse across platforms.
- **Feature Modules**: Each feature (e.g., login, main) is isolated for independent development and testing.

---

For more details, see the code comments and module documentation.

# Compose App

RunComposeAppWasmJsBrowserRun:
```bash
./gradlew :composeApp:wasmJsBrowserRun
```
