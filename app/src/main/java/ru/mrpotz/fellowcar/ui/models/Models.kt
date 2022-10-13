package ru.mrpotz.fellowcar.ui.models

data class UiRequestedAction(
    val id: String,
)

data class SnackbarErrorMessage(
    val description: String,
    val uiRequestedAction: UiRequestedAction?,
) {
    val hasAction: Boolean
        get() = uiRequestedAction != null
}
