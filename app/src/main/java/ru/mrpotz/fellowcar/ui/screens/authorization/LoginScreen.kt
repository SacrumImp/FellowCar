package ru.mrpotz.fellowcar.ui.screens.authorization

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.mrpotz.fellowcar.FellowCarApp
import ru.mrpotz.fellowcar.ScaffoldCompositionLocal
import ru.mrpotz.fellowcar.logics.*
import ru.mrpotz.fellowcar.ui.models.SnackbarDataUi
import ru.mrpotz.fellowcar.ui.screens.home.HomeScreen
import ru.mrpotz.fellowcar.ui.screens.onboarding.FellowCarTitleHeader
import ru.mrpotz.fellowcar.ui.theme.LinkColor
import ru.mrpotz.fellowcar.utils.Form
import ru.mrpotz.fellowcar.utils.TextAssociatedContainer
import ru.mrpotz.fellowcar.utils.TextContainer
import ru.mrpotz.fellowcar.utils.ValidationContainerImpl
import java.util.concurrent.Flow

class LoginScreenModel(private val navigator: Navigator, val userRepository: UserRepository) :
    ScreenModel {
    val snackbarMessages: MutableStateFlow<SnackbarDataUi?> = MutableStateFlow(null)

    private val validationContainer = ValidationContainerImpl()
    val email = TextAssociatedContainer(validationContainer)
    val password = TextAssociatedContainer(validationContainer)
    private val form = Form(validationContainer) {}.apply {
        input(password.fieldId, password.valueContainer) {
            isNotEmpty()
        }
        input(email.fieldId, email.valueContainer) {
            isNotEmpty()
            isEmail()
        }
    }

    init {
        coroutineScope.launch {
            form.startValidation()
        }
    }

    fun onForgotClick() {
        // TODO: snack bar with message - functionality is yet to be implemented
    }

    fun onSnackbarResult(snackbarResult: SnackbarResult): Unit {
        snackbarMessages.value = null
    }

    fun onRegisterClick() {
        if (RegistrationScreen in navigator.items) {
            navigator.popUntil { it == RegistrationScreen }
        } else {
//            navigator.pop()
            navigator.push(RegistrationScreen)
        }
    }

    fun onLoginClick() {
        val formResult = form.validate()
        if (formResult.success()) {
            coroutineScope.launch {
                val loggedInUser =
                    userRepository.logUser(loggingData = LoggingData(email = ValidEmail(email.valueContainer.value!!.toString()),
                        password = Password(password = password.valueContainer.value!!.toString())))
                if (loggedInUser.isFailure) {
                    val message = (loggedInUser.exceptionOrNull() as? UserError)?.message
                    if (message != null) {
                        snackbarMessages.value =
                            SnackbarDataUi.create(message, null, duration = SnackbarDuration.Short)
                    }
                } else {
                    navigator.popAll()
                    navigator.replace(HomeScreen)
                }
            }
        } else {
            snackbarMessages.value = SnackbarDataUi.create("fill all required fields",
                null,
                duration = SnackbarDuration.Short)
        }
    }
}

suspend fun SnackbarHostState.showSnackbar(snackbarDataUi: SnackbarDataUi): SnackbarResult {
    return showSnackbar(snackbarDataUi.message, snackbarDataUi.actionLabel, snackbarDataUi.duration)
}

object LoginScreen : Screen {
    @Composable
    override fun Content() {
        val localNavigator = LocalNavigator.currentOrThrow
        val model = rememberScreenModel {
            LoginScreenModel(localNavigator, FellowCarApp.dependencies.userManager)
        }

        val snackbarMessage by model.snackbarMessages.collectAsState()
        val scaffoldState = ScaffoldCompositionLocal.current
        if (snackbarMessage != null) {
            LaunchedEffect(key1 = scaffoldState) {
                val result = scaffoldState.snackbarHostState.showSnackbar(snackbarMessage!!)
                model.onSnackbarResult(result)
            }
        }

        val email by model.email.flow.collectAsState()
        val password by model.password.flow.collectAsState()

        LoginScreenComposable(emailValue = email,
            passwordValue = password,
            onForgotClick = model::onForgotClick,
            onRegisterClick = model::onRegisterClick,
            onLoginClick = model::onLoginClick)
    }
}

@Composable
fun PasswordErrorableTextField(
    value: TextContainer.DataClass = TextContainer.DataClass("", null, null),
    label: String = "Password",

    ) {
    var passwordVisibility by remember { mutableStateOf(false) }

    TextField(value = value.value.toString(),
        onValueChange = { value.callback?.invoke(it) },
        singleLine = true,
        label = {
            Text(label)
        },
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            Row {
                if (value.error != null) Icon(Icons.Filled.Info,
                    value.error.description,
                    tint = MaterialTheme.colors.error,
                    modifier = Modifier.align(Alignment.CenterVertically))

                IconButton(onClick = {
                    passwordVisibility = !passwordVisibility
                }, modifier = Modifier.align(Alignment.CenterVertically)) {
                    val image = if (passwordVisibility) Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff
                    val description = if (passwordVisibility) "Hide password" else "Show password"
                    Icon(imageVector = image, description)
                }
            }
        },
        isError = value.error != null,
        modifier = Modifier.fillMaxWidth())
}

@Composable
fun ErrorableTextField(
    label: String,
    value: TextContainer.DataClass = TextContainer.DataClass("", null, null),
) {
    TextField(value = value.value.toString(),
        onValueChange = { value.callback?.invoke(it) },
        isError = value.error != null,
        trailingIcon = {
            if (value.error != null) Icon(Icons.Filled.Info,
                value.error.description,
                tint = MaterialTheme.colors.error)
        },
        singleLine = true,
        label = {
            Text(label)
        },
        modifier = Modifier.fillMaxWidth())
}

@Composable
fun LoginScreenComposable(
    emailValue: TextContainer.DataClass = TextContainer.DataClass("", null),
    passwordValue: TextContainer.DataClass = TextContainer.DataClass("", null),
    onForgotClick: () -> Unit = { },
    onRegisterClick: () -> Unit = { },
    onLoginClick: () -> Unit = { },
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        FellowCarTitleHeader()
        Column(Modifier
            .fillMaxWidth()
            .padding(16.dp)) {

            Text(text = "Welcome to FellowCar,",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = "Share and schedule rides with fellow workers and save money, time and the environment.",
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.size(16.dp))
            ErrorableTextField(label = "Email", value = emailValue)
            Spacer(Modifier.size(8.dp))
            PasswordErrorableTextField(passwordValue)
            Spacer(modifier = Modifier.size(16.dp))
            TextButton(onClick = onForgotClick, Modifier.align(Alignment.End)) {
                Text(text = "Forgot password",
                    color = LinkColor,
                    textDecoration = TextDecoration.Underline)
            }
        }

        Column(Modifier.fillMaxWidth()) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                Text("Don't have an account? ", Modifier.alignByBaseline())
                TextButton(onClick = onRegisterClick, Modifier.alignByBaseline()) {
                    Text(text = "Register",
                        color = LinkColor,
                        textDecoration = TextDecoration.Underline)

                }
            }
            Spacer(Modifier.size(16.dp))
            Button(onClick = onLoginClick, modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)) {
                Text(text = "Login", style = MaterialTheme.typography.h6)
            }
        }
    }
}

@Preview(backgroundColor = 0xFFFFFFFF)
@Composable
fun LoginScreenPreview() {
    LoginScreenComposable()
}
