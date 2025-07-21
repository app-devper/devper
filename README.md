# DevPer - Compose Multiplatform Project

A modern, modular Compose Multiplatform application built with Kotlin, supporting Android, iOS, Desktop, and WebAssembly platforms.

## 🏗️ Project Structure

This project follows a clean, modular architecture with clear separation of concerns:

```
devper/
├── 📁 build-logic/             # Gradle build conventions and shared build logic
│   └── convention/             # Custom Gradle plugins for consistent builds
├── 📱 composeApp/              # Main Compose Multiplatform application
│   ├── src/
│   │   ├── androidMain/        # Android-specific implementations
│   │   ├── commonMain/         # Shared code across all platforms
│   │   ├── desktopMain/        # Desktop (JVM) specific code
│   │   ├── iosMain/           # iOS-specific implementations
│   │   └── wasmJsMain/        # WebAssembly JavaScript target
│   └── build.gradle.kts
├── 🧩 core/                   # Core modules (foundation layer)
│   ├── common/                # Common utilities, extensions, and base classes
│   ├── data/                  # Data layer (repositories, data sources, networking)
│   ├── design/                # Design system (UI components, themes, tokens)
│   └── domain/                # Domain layer (business logic, use cases, models)
├── 🎯 feature/                # Feature modules (presentation layer)
│   ├── login/                 # Authentication and login functionality
│   └── main/                  # Main application features
├── 🔧 shared/                 # Shared resources and utilities
├── 🖥️ server/                 # Backend server implementation
├── 🍎 iosApp/                 # Native iOS app wrapper
├── 📦 kotlin-js-store/        # JS/WASM build artifacts and packages
├── 🔐 keystore/               # Android signing certificates
└── ⚙️ gradle/                 # Gradle wrapper and dependency management
    └── libs.versions.toml     # Centralized dependency versions
```

## 🎨 Architecture & Design Patterns

### Clean Architecture
The project implements **Clean Architecture** principles with clear layer separation:

```
┌─────────────────────────────────────────────┐
│                Presentation                 │ ← feature/* modules
│            (Compose UI, ViewModels)         │
├─────────────────────────────────────────────┤
│                 Domain                      │ ← core/domain
│           (Use Cases, Models)               │
├─────────────────────────────────────────────┤
│                  Data                       │ ← core/data
│         (Repositories, Data Sources)        │
├─────────────────────────────────────────────┤
│                Framework                    │ ← Platform-specific
│        (Platform implementations)           │
└─────────────────────────────────────────────┘
```

### Key Design Patterns

#### 1. **Modular Architecture**
- **Feature Modules**: Each feature is self-contained with its own UI, ViewModels, and navigation
- **Core Modules**: Shared business logic, data access, and design system
- **Platform Modules**: Platform-specific implementations in `*Main` source sets

#### 2. **MVVM (Model-View-ViewModel)**
- **View**: Compose UI components (declarative UI)
- **ViewModel**: State management and business logic orchestration
- **Model**: Data models and domain entities

#### 3. **Repository Pattern**
- Data access abstraction in `core/data`
- Separation of local and remote data sources
- Consistent API across different platforms

#### 4. **Dependency Injection**
- Modular DI setup for loose coupling
- Platform-specific dependency provision
- Testable architecture with easy mocking

#### 5. **State Management**
- Unidirectional data flow
- Reactive state updates using StateFlow/State
- Immutable state objects

#### 6. **Design System**
- Centralized UI components in `core/design`
- Consistent theming across platforms
- Reusable design tokens and components

## 🚀 Getting Started

### Prerequisites
- JDK 17 or higher
- Android Studio (for Android development)
- Xcode (for iOS development)
- Node.js (for WASM target)

### Running the Application

#### Android
```bash
./gradlew :composeApp:assembleDebug
# or open in Android Studio
```

#### Desktop
```bash
./gradlew :composeApp:run
```

#### iOS
```bash
./gradlew :composeApp:iosSimulatorArm64Test
# or open iosApp/iosApp.xcodeproj in Xcode
```

#### WebAssembly
```bash
./gradlew :composeApp:wasmJsBrowserRun
```

### Development Workflow

#### Building
```bash
# Build all targets
./gradlew build

# Build specific target
./gradlew :composeApp:assembleDebug
```

#### Testing
```bash
# Run all tests
./gradlew test

# Run specific module tests
./gradlew :core:domain:test
```

## 📦 Module Dependencies

```
composeApp
├── core:common
├── core:data
├── core:design
├── core:domain
├── feature:login
└── feature:main

feature:login
├── core:common
├── core:design
└── core:domain

feature:main
├── core:common
├── core:design
└── core:domain

core:data
├── core:common
└── core:domain

core:design
└── core:common

core:domain
└── core:common
```

## 🛠️ Technology Stack

### Core Technologies
- **Kotlin Multiplatform**: Shared business logic across platforms
- **Compose Multiplatform**: Declarative UI framework
- **Gradle**: Build system with custom conventions

### Platform Support
- **Android**: Native Android app
- **iOS**: Native iOS app with Swift wrapper
- **Desktop**: JVM-based desktop application
- **Web**: WebAssembly target for browsers

### Libraries & Frameworks
- **Coroutines**: Asynchronous programming
- **StateFlow**: Reactive state management
- **Serialization**: JSON serialization/deserialization
- **Navigation**: Multi-platform navigation

## 🧪 Testing Strategy

- **Unit Tests**: Business logic testing in domain layer
- **Integration Tests**: Repository and use case testing
- **UI Tests**: Compose UI testing
- **Platform Tests**: Platform-specific functionality testing

## 📝 Code Style & Conventions

- **Kotlin Coding Conventions**: Following official Kotlin style guide
- **Clean Code Principles**: Readable, maintainable code
- **SOLID Principles**: Object-oriented design principles
- **Dependency Rule**: Dependencies point inward (Clean Architecture)

## 🔧 Build Configuration

The project uses **Gradle Version Catalogs** (`gradle/libs.versions.toml`) for centralized dependency management and custom build logic in `build-logic/` for consistent configuration across modules.

## 📱 Platform-Specific Features

### Android
- Material Design 3 components
- Android-specific navigation
- Platform APIs integration

### iOS
- Native iOS navigation
- Swift interoperability
- iOS-specific UI adaptations

### Desktop
- Desktop-optimized layouts
- System integration features
- File system access

### WebAssembly
- Browser-optimized performance
- Web-specific APIs
- Progressive Web App capabilities

---

*This project demonstrates modern Kotlin Multiplatform development practices with a focus on maintainability, testability, and scalability.*
