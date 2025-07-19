package com.devper.app.core.design.state

sealed class UIComponentState {

    data object Show : UIComponentState()

    data object HalfShow : UIComponentState()

    data object Hide : UIComponentState()
}
