package ru.mrpotz.fellowcar.logics

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.single
import ru.mrpotz.fellowcar.models.Profile
import ru.mrpotz.fellowcar.models.User
import ru.mrpotz.fellowcar.ui.models.PasswordHash
import ru.mrpotz.fellowcar.ui.models.UserLocal
import ru.mrpotz.fellowcar.utils.StringHasher

@JvmInline
value class ValidEmail(val email: String)

@JvmInline
value class Password(val password: CharSequence)

data class LoggingData(
    val email: ValidEmail,
    val password: Password,
)

data class RegisterData(
    val fullName: CharSequence,
    val email: ValidEmail,
    val password: Password,
    val passwordConfirmation: Password,
)

/**
 * MEMORY
 *
 * DATA STORE:
 * local info to use to refer to backend actual version of the user
 *
 * MOCK TEMPORARY VERSION:
 * local "actual" version of the REGISTERED user. But have to register first
 * If not registered, this one is empty.
 *
 */
enum class UserStoreType {
    LOCAL, REMOTE_MOCK
}

// TODO A-69: move to User - define CRUD for local storage
class UserDataStore(val context: Context, val userStore: UserStoreType = UserStoreType.LOCAL) {
    private val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(name = userStore.name)

    private val MOCK_PASSWORD_HASH = intPreferencesKey("mock_password")

    suspend fun getUser(): UserLocal? {
        return context.userDataStore.data.single().let {
            UserLocal.createFromPreferences(it)
        }
    }

    suspend fun updateUser(userLocal: UserLocal?) {
        context.userDataStore.edit {
            if (userLocal == null) {
                UserLocal.cleanPreferences(it)
            } else {
                userLocal.writePreferences(it)
            }
        }

    }

    companion object {

    }
}

class UserLocalConverter() {
    fun convertUserLocalToDomain(userLocal: UserLocal): User {
        return User(
            id = userLocal.userId,
            name = userLocal.name,
            roles = listOf(),
            joinedCommunities = listOf(),
            rating = 5.0,
            profile = Profile(description = null),
            email = ValidEmail(userLocal.email)
        )
    }

    fun dtoToUserLocal(user: User, passwordHash: PasswordHash): UserLocal {
        return UserLocal(
            userId = user.id,
            name = user.name,
            email = user.name,
            passwordHash = passwordHash
        )
    }
}

class UserRepository(
    private val context: Context,
    private val userLocalConverter: UserLocalConverter,
) {
    private val localUserDataStore = UserDataStore(context, userStore = UserStoreType.LOCAL)
    private val remoteMockUserDataStore =
        UserDataStore(context, userStore = UserStoreType.REMOTE_MOCK)

    init {

    }

    suspend fun tryLoadLocalUser(): UserLocal? {
        return localUserDataStore.getUser()
    }

    suspend fun syncUser() {
        TODO()
    }

    suspend fun getCurrentLoggedUser(): Result<User> {
        val datastoreUser = tryLoadLocalUser()
            ?: return Result.failure(UserError.NoUserAtPreferences)
        return userLocalConverter.convertUserLocalToDomain(datastoreUser).let {
            Result.success(it)
        }
    }

    suspend fun logUser(loggingData: LoggingData): Result<User> {
        delay(300L) // connection imitation
        // TODO: A-69 add crud for multiple users
        val getCurrentRegisteredUser = remoteMockUserDataStore.getUser()
        if (getCurrentRegisteredUser == null || !getCurrentRegisteredUser.compareAgainstLoginData(
                loggingData)
        ) {
            // users are equal, there is such already registered user
            return Result.failure(UserError.LoginCredentialsInvalid)
        }
        val user = userLocalConverter.convertUserLocalToDomain(userLocal = getCurrentRegisteredUser)
        return Result.success(user)
    }

    suspend fun isUserLoggedIn(): Boolean {
        return tryLoadLocalUser() != null
    }

    // valid passed here is considered to be correct, this is regulated by value classes
    suspend fun registerUser(registerData: RegisterData): Result<User> {
        // ... send request here or something like that
        // MOCK register scenario
        return mockScenarioSuccessRegister(registerData)
    }

    private suspend fun mockScenarioSuccessRegister(registerData: RegisterData): Result<User> {
        delay(300L) // connection imitation
        // TODO: A-69 add crud for multiple users
        val getCurrentRegisteredUser = remoteMockUserDataStore.getUser()
        val newUser =
            if (getCurrentRegisteredUser != null && getCurrentRegisteredUser.compareAgainsRegisterData(
                    registerData)
            ) {
                // users are equal, there is such already registered user
                return Result.failure(UserError.GivenUserAlreadyRegistered)
            } else {
                val newDomainUser = User(
                    id = "mock_user_id",
                    name = registerData.fullName.toString(),
                    email = registerData.email,
                    roles = listOf(),
                    joinedCommunities = listOf(),
                    rating = null,
                    profile = Profile(null)
                )
                newDomainUser
            }
        val newLocalUser =
            userLocalConverter.dtoToUserLocal(newUser, passwordHash = PasswordHash(StringHasher.getStringHash(
                registerData.password.password) ?: return Result.failure(
                UserError.ImpossibleToHashPassword)))
        remoteMockUserDataStore.updateUser(newLocalUser)
        return Result.success(newUser)
    }
}

sealed class UserError(message: String) : Throwable(message) {
    object NoUserAtPreferences : UserError("no users logged in on the device")
    object ImpossibleToHashPassword : UserError("can't process password")
    object NetworkLoadFailed : UserError("network load failed")
    object GivenUserAlreadyRegistered : UserError("user already registered")
    object LoginCredentialsInvalid : UserError("user was not found")
}
