package ru.mrpotz.fellowcar.ui.screens.authorization

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
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
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ru.mrpotz.fellowcar.FellowCarApp
import ru.mrpotz.fellowcar.logics.UserRepository
import ru.mrpotz.fellowcar.ui.screens.onboarding.FellowCarTitleHeader
import ru.mrpotz.fellowcar.ui.theme.LinkColor

class LoginScreenModel(val localNavigator: Navigator, val userRepository: UserRepository) : ScreenModel {
    fun onPasswordInput(newValue : String, ) {
    }

    fun onEmailInputChange(newValue : String, ) {
    }

    fun onForgotClick() {

    }

    fun onRegisterClick() {

    }

    fun onLoginClick() {

    }
}


object LoginScreen : Screen {
    @Composable
    override fun Content() {
        val localNavigator = LocalNavigator.currentOrThrow
        val model = rememberScreenModel {
            LoginScreenModel(localNavigator, FellowCarApp.dependencies.userManager)
        }
        LoginScreen()

        Column(modifier = Modifier.fillMaxSize()) {
            Text("this is login screen")
        }
    }
}

@Composable
fun LoginScreen(
    emailValue: String = "",
    onEmailInputChange: (newValue : String, ) -> Unit = { },
    passwordValue: String = "",
    onPasswordInput: (newValue : String, ) -> Unit = { },
    onForgotClick: () -> Unit = { },
    onRegisterClick: () -> Unit = { },
    onLoginClick: () -> Unit = { },
) {

    Column(
        modifier = Modifier
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
            TextField(value = emailValue,
                onValueChange = onEmailInputChange,
                singleLine = true,
                label = {
                    Text("Email")
                },
                modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.size(8.dp))
            TextField(
                value = passwordValue, onValueChange = onPasswordInput, singleLine = true,
                label = {
                    Text("Password")
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.size(16.dp))

            TextButton(onClick = onForgotClick, Modifier.align(Alignment.End)) {
                Text(text = "Forgot password",
                    color = LinkColor,
                    textDecoration = TextDecoration.Underline)

            }
        }

        Column(Modifier.fillMaxWidth()) {
            Row(Modifier
                .fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
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

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}
