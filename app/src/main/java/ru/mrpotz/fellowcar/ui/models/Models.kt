package ru.mrpotz.fellowcar.ui.models

import androidx.compose.material.SnackbarData
import androidx.compose.material.SnackbarDuration
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import ru.mrpotz.fellowcar.logics.LoggingData
import ru.mrpotz.fellowcar.logics.RegisterData
import ru.mrpotz.fellowcar.logics.ValidEmail
import ru.mrpotz.fellowcar.utils.IdIssuer
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
    val id: Long,
) : SnackbarData {
    override fun dismiss() {}
    override fun performAction() {}

    companion object {
        val idIssuer by lazy(LazyThreadSafetyMode.NONE) { IdIssuer() }

        fun create(
            message: String,
            actionLabel: String?,
            duration: SnackbarDuration,
        ): SnackbarDataUi {
            return SnackbarDataUi(message, actionLabel, duration, idIssuer.createId())
        }
    }
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
    val email: ValidEmail,
    val passwordHash: PasswordHash,
) {
    fun compareAgainsRegisterData(registerData: RegisterData): Boolean {
        val passwordHash = StringHasher.getStringHash(registerData.password.password) ?: return false
        val comparedUser = UserLocal(
            userId = this.userId,
            name = registerData.fullName.toString(),
            email = registerData.email,
            passwordHash = PasswordHash(passwordHash)
        )
        return comparedUser == this
    }

    fun compareAgainstLoginData(loginData: LoggingData): Boolean {
        val passwordHash = StringHasher.getStringHash(loginData.password.password) ?: return false
        val comparedUser = UserLocal(
            userId = this.userId,
            name = this.name,
            email = loginData.email,
            passwordHash = PasswordHash(passwordHash)
        )
        return comparedUser == this
    }

    fun writePreferences(preferences: MutablePreferences) {
        preferences.apply {
            this[NAME_KEY] = name
            this[EMAIL] = email.email
            this[USER_ID] = userId
            this[PASSWORD_HASH] = passwordHash.value
        }
    }

    companion object {
        val NAME_KEY = stringPreferencesKey("name")
        val EMAIL = stringPreferencesKey("email")
        val USER_ID = stringPreferencesKey("user_id")
        val PASSWORD_HASH = stringPreferencesKey("password_hash")

        @JvmStatic
        fun cleanPreferences(it: MutablePreferences) {
            it.clear()
        }

        @JvmStatic
        fun createFromPreferences(it: Preferences): UserLocal? {
            return UserLocal(
                userId = it[USER_ID] ?: return null,
                name = it[NAME_KEY] ?: return null,
                email = it[EMAIL]?.let { ValidEmail(it) } ?: return null,
                passwordHash = PasswordHash(it[PASSWORD_HASH] ?: return null)
            )
        }
    }
}
