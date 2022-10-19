package ru.mrpotz.fellowcar.ui.models

import androidx.compose.material.SnackbarData
import androidx.compose.material.SnackbarDuration
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import ru.mrpotz.fellowcar.logics.LoggingData
import ru.mrpotz.fellowcar.logics.RegisterData
import ru.mrpotz.fellowcar.utils.Mock
import ru.mrpotz.fellowcar.utils.StringHasher

@JvmInline
@Mock
value class PasswordHash(val value: String)

data class UiRequestedAction(
    val id: String,
)

data class SnackbarDataUi(
    override val message: String,
    override val actionLabel: String?,
    override val duration: SnackbarDuration,
) : SnackbarData {
    override fun dismiss() {}
    override fun performAction() {}
}

data class SnackbarErrorMessage(
    val description: String,
    val uiRequestedAction: UiRequestedAction?,
) {
    val hasAction: Boolean
        get() = uiRequestedAction != null
}

data class UserLocal(
    val userId: String,
    val name: String,
    val email: String,
    val passwordHash: PasswordHash,
) {
    fun compareAgainsRegisterData(registerData: RegisterData): Boolean {
        val comparedUser = UserLocal(
            userId = this.userId,
            name = registerData.fullName.toString(),
            email = registerData.email.email,
            passwordHash = passwordHash
        )
        return comparedUser == this
    }

    fun compareAgainstLoginData(loginData: LoggingData): Boolean {
        val passwordHash = StringHasher.getStringHash(loginData.password.password) ?: return false
        val comparedUser = UserLocal(
            userId = this.userId,
            name = this.name,
            email = loginData.email.email,
            passwordHash = PasswordHash(passwordHash)
        )
        return comparedUser == this
    }

    fun writePreferences(preferences: MutablePreferences) {
        preferences.apply {
            this[NAME_KEY] = name
            this[EMAIL] = email
            this[USER_ID] = userId
        }
    }

    companion object {
        val NAME_KEY = stringPreferencesKey("name")
        val EMAIL = stringPreferencesKey("email")
        val USER_ID = stringPreferencesKey("user_id")
        val PASSWORD_HASH = stringPreferencesKey("password_hash")

        @JvmStatic
        fun cleanPreferences(it: Preferences) {

        }

        @JvmStatic
        fun createFromPreferences(it: Preferences): UserLocal? {
            return UserLocal(
                userId = it[USER_ID] ?: return null,
                name = it[NAME_KEY] ?: return null,
                email = it[EMAIL] ?: return null,
                passwordHash = PasswordHash(it[PASSWORD_HASH] ?: return null)
            )
        }
    }
}
