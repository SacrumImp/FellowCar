package ru.mrpotz.fellowcar.ui.screens.authorization

import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import ru.mrpotz.fellowcar.ui.screens.onboarding.FellowCarTitleHeader
import ru.mrpotz.fellowcar.ui.theme.LinkColor
import ru.mrpotz.fellowcar.utils.Form
import ru.mrpotz.fellowcar.utils.TextAssociatedContainer
import ru.mrpotz.fellowcar.utils.ValidationContainerImpl

class RegistrationScreenModel : ScreenModel {
    private val validationContainer = ValidationContainerImpl()
    val fullName = TextAssociatedContainer(validationContainer)
    val email = TextAssociatedContainer(validationContainer)
    val password = TextAssociatedContainer(validationContainer)
    val passwordConfirmation = TextAssociatedContainer(validationContainer)

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
//            assert("passwords don't match") { confirmationContainer ->
////                confirmationContainer.value == password.value
//            }
        }
    }
}

object RegistrationScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel() {
            RegistrationScreenModel()
        }
//        val name by viewModel.name.collectAsState()
//        val passwordConfirmation by viewModel.passwordConfirmation.collectAsState()
//        val email by viewModel.email.collectAsState()
//        val password by viewModel.password.collectAsState()
//
//        RegistrationScreen(
//            onFullNameValue = viewModel::onFullNameValue,
//            onEmailValue = viewModel::onEmailValue,
//            onPasswordConfirmationValue = viewModel::onPasswordConfirmationValue,
//            onPasswordValue = viewModel::onPasswordValue,
//            fullName = name,
//            passwordConfirmationValue = passwordConfirmation,
//            passwordValue = password,
//            email = email
//        )
    }
}

@Composable
fun RegistrationScreen(
    fullName: String = "",
    onFullNameValue: (newValue: String) -> Unit = { },
    email: String = "",
    onEmailValue: (newValue: String) -> Unit = { },
    passwordValue: String = "",
    onPasswordValue: (newValue: String) -> Unit = { },
    passwordConfirmationValue: String = "",
    onPasswordConfirmationValue: (newValue: String) -> Unit = { },
    onLoginClick: () -> Unit = { },
) {
    val scrollableState = rememberScrollState()
    Column(
        modifier = Modifier
            .verticalScroll(scrollableState)
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .fillMaxHeight()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        FellowCarTitleHeader()

        Column(Modifier
            .fillMaxWidth()
            .padding(16.dp)) {

            Text(text = "Register",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.size(8.dp))
            TextField(value = fullName,
                onValueChange = onFullNameValue,
                singleLine = true,
                label = {
                    Text("Full name")
                },
                modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.size(8.dp))
            TextField(value = email,
                onValueChange = onEmailValue,
                singleLine = true,
                label = {
                    Text("Email")
                },
                modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.size(8.dp))

            TextField(
                value = passwordValue, onValueChange = onPasswordValue, singleLine = true,
                label = {
                    Text("Password")
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.size(8.dp))
            TextField(
                value = passwordConfirmationValue,
                onValueChange = onPasswordConfirmationValue,
                singleLine = true,
                label = {
                    Text("Password Confirmation")
                },
                modifier = Modifier.fillMaxWidth()
            )
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
            Button(onClick = onLoginClick, modifier = Modifier
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
