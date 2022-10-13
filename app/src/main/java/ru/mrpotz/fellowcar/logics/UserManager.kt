package ru.mrpotz.fellowcar.logics

import ru.mrpotz.fellowcar.models.User

data class LoggingData(
    val email: CharSequence,
    val password: CharSequence,
)

data class RegisterData(
    val fullName: CharSequence,
    val email: CharSequence,
    val password: CharSequence,
    val passwordConfirmation: CharSequence,
)

class UserManager {
    suspend fun syncUser() {
        TODO()
    }

    suspend fun getCurrentLoggedUser(): Result<User> {
        TODO()
    }

    suspend fun logUser(loggingData: LoggingData): Result<User> {
        TODO()
    }

    suspend fun registerUser(registerData: RegisterData): Result<User> {
        TODO()
    }
}
