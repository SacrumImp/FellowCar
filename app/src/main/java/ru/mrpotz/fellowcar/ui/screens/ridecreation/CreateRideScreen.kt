package ru.mrpotz.fellowcar.ui.screens.ridecreation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen

class CreateRideScreenModel : ScreenModel { 
}

object CreateRideScreen : Screen { 
    @Composable
    override fun Content() { 
        val viewModel = rememberScreenModel() { 
            CreateRideScreenModel()
        }
//       BackdropScaffold(appBar = { /*TODO*/ }, backLayerContent = { /*TODO*/ }) {
//
//       }
    } 
}

@Composable
fun CreateRideScreenComposable() { 

}

@Preview
@Composable
fun CreateRideScreenPreview() { 
    
}
