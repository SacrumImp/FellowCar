package ru.mrpotz.fellowcar.ui.screens.authorization

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
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
import ru.mrpotz.fellowcar.ui.screens.onboarding.FellowCarTitleHeader
import ru.mrpotz.fellowcar.ui.theme.LinkColor
import ru.mrpotz.fellowcar.utils.Form
import ru.mrpotz.fellowcar.utils.TextAssociatedContainer
import ru.mrpotz.fellowcar.utils.TextContainer
import ru.mrpotz.fellowcar.utils.ValidationContainerImpl

class RegistrationScreenModel(val navigator: Navigator, val userRepository: UserRepository) :
    ScreenModel {
    private val validationContainer = ValidationContainerImpl()
    val fullName = TextAssociatedContainer(validationContainer)
    val email = TextAssociatedContainer(validationContainer)
    val password = TextAssociatedContainer(validationContainer)
    val passwordConfirmation = TextAssociatedContainer(validationContainer)
    val snackbarMessages: MutableStateFlow<SnackbarDataUi?> = MutableStateFlow(null)

    private val form = Form(validationContainer) {
    }.apply {
        input(fullName.fieldId, fullName.valueContainer) {
            isNotEmpty()
        }
        input(email.fieldId, email.valueContainer) {
            isNotEmpty()
            isEmail()
        }
        input(password.fieldId, password.valueContainer) {
            isNotEmpty()
        }
        input(passwordConfirmation.fieldId, passwordConfirmation.valueContainer) {
            isNotEmpty()
            assert("passwords must match") { confirmationContainer ->
                confirmationContainer.value == password.valueContainer.value
            }
        }
    }

    init {
        coroutineScope.launch {
            form.startValidation()
        }
    }

    fun onLoginClick() {
        if (LoginScreen in navigator.items) {
            navigator.popUntil { it == LoginScreen }
        } else {
//            navigator.pop()
            navigator.push(LoginScreen)
        }
    }

    fun onSnackbarResult(snackbarResult: SnackbarResult): Unit {
        snackbarMessages.value = null
    }

    fun onContinueClick() {
        val formResult = form.validate()
        if (formResult.success()) {
            coroutineScope.launch {
                val registeredUser = userRepository.registerUser(
                    registerData = RegisterData(
                        email = ValidEmail(email.valueContainer.value!!.toString()),
                        password = Password(password = password.valueContainer.value!!.toString()),
                        passwordConfirmation = Password(password = passwordConfirmation.valueContainer.value!!.toString()),
                        fullName = fullName.valueContainer.value!!
                    )
                )
                if (registeredUser.isFailure) {
                    val message = (registeredUser.exceptionOrNull() as? UserError)?.message
                    if (message != null) {
                        Log.d("LoginScreen", "message: $message")
                        snackbarMessages.value =
                            SnackbarDataUi.create(message, null, duration = SnackbarDuration.Short)
                    }
                } else {
                    snackbarMessages.value = SnackbarDataUi.create("Register success",
                        null,
                        duration = SnackbarDuration.Short)
                }
            }
        } else {
            snackbarMessages.value = SnackbarDataUi.create("fill all required fields",
                null,
                duration = SnackbarDuration.Short)
        }
    }
}

object RegistrationScreen : Screen {
    @Composable
    override fun Content() {
        val localNavigator = LocalNavigator.currentOrThrow

        val viewModel = rememberScreenModel() {
            RegistrationScreenModel(localNavigator, FellowCarApp.dependencies.userManager)
        }
        val snackbarMessage by viewModel.snackbarMessages.collectAsState()
        val scaffoldState = ScaffoldCompositionLocal.current
        if (snackbarMessage != null) {
            LaunchedEffect(key1 = scaffoldState) {
                val result = scaffoldState.snackbarHostState.showSnackbar(snackbarMessage!!)
                viewModel.onSnackbarResult(result)
            }
        }

        val name by viewModel.fullName.flow.collectAsState()
        val passwordConfirmation by viewModel.passwordConfirmation.flow.collectAsState()
        val email by viewModel.email.flow.collectAsState()
        val password by viewModel.password.flow.collectAsState()

        RegistrationScreen(
            fullName = name,
            email = email,
            password = password,
            passwordConfirmation = passwordConfirmation,
            onLoginClick = viewModel::onLoginClick,
            onContinueClick = viewModel::onContinueClick
        )
    }
}

@Composable
fun RegistrationScreen(
    fullName: TextContainer.DataClass = TextContainer.DataClass("", null),
    email: TextContainer.DataClass = TextContainer.DataClass("", null),
    password: TextContainer.DataClass = TextContainer.DataClass("", null),
    passwordConfirmation: TextContainer.DataClass = TextContainer.DataClass("", null),
    onLoginClick: () -> Unit = { },
    onContinueClick: () -> Unit = { },
) {
    val scrollableState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .verticalScroll(scrollableState)
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        FellowCarTitleHeader()


        Column(Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            Text(text = "Register",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h5,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.size(24.dp))
            ErrorableTextField(label = "Full Name", value = fullName)
            Spacer(Modifier.size(8.dp))
            ErrorableTextField(label = "Email", value = email)
            Spacer(Modifier.size(8.dp))
            PasswordErrorableTextField(password)
            Spacer(modifier = Modifier.size(8.dp))
            PasswordErrorableTextField(passwordConfirmation, "Confirm Password")
        }

        Column(Modifier.fillMaxWidth()) {
            Row(Modifier
                .fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                Text("Already have an account? ", Modifier.alignByBaseline())
                TextButton(onClick = onLoginClick, Modifier.alignByBaseline()) {
                    Text(text = "Login",
                        color = LinkColor,
                        textDecoration = TextDecoration.Underline)

                }
            }
            Spacer(Modifier.size(16.dp))
            Button(onClick = onContinueClick, modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)) {
                Text(text = "Continue →", style = MaterialTheme.typography.h6)
            }
        }
    }
}

@Preview
@Composable
fun RegistrationScreenPreview() {

}
