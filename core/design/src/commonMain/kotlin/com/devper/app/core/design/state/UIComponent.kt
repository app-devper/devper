package com.devper.app.core.design.state

sealed class UIComponent {

    data class Dialog(
        val title: String,
        val description: String
    ) : UIComponent()

    data class ToastSimple(
        val title: String,
    ) : UIComponent()

    data class None(
        val message: String,
    ) : UIComponent()

}