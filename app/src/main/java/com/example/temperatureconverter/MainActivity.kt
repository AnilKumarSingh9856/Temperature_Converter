package com.example.temperatureconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.temperatureconverter.ui.theme.TemperatureConverterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TemperatureConverterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }
            }
        }
    }
}

@Composable
fun MyApp() {
    // create an instance of the ViewModelForTemp class
    val viewModel: ViewModelForTemp = viewModel()

    // display the user interface

    MainScreen(
        isFahrenheit = viewModel.isFahrenheit,
        result = viewModel.convertedTemperatures,
        convertTemp = {viewModel.calculateConversion(it)},
        toggleSwitch = { viewModel.doSwitchToggle()}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    // state of the toggle switch. true = Fahrenheit, false= Celsius
    isFahrenheit: Boolean,

    // calculated result. this  will be updated when the calculate button is pressed
    result:String,

    // calculate the temperature when the calculate button is pressed
    convertTemp: (String) -> Unit,

    toggleSwitch:() -> Unit){

    // input text box state change handlers
    var inputTextState by remember {
        mutableStateOf("")
    }

    // update the value of inputTextState when a letter is input
    fun onTextChange(newValue: String){
        inputTextState = newValue
    }

    // define ui elements here
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.headlineMedium,
            text = "Temperature Conversion App"
        )
        Card(elevation = CardDefaults.cardElevation(defaultElevation = 9.dp),
            colors = CardDefaults.cardColors(Color.White),
            ) {
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                Switch(checked = isFahrenheit, onCheckedChange = {toggleSwitch()})
                OutlinedTextField(
                    value = inputTextState, 
                    onValueChange = { onTextChange(it)},
                    label = { Text(text = "Enter temperature")},
                    modifier = Modifier.padding(16.dp),
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyMedium,
                    trailingIcon = {
                        Text(text = if (isFahrenheit) "\u2109" else "\u2103")
                    }
                )
            } // end row
        } // end card

        Text(text = "Result: $result",
            Modifier.padding(16.dp),
            style =  MaterialTheme.typography.headlineMedium
        )
        
        Button(onClick = { convertTemp(inputTextState) }) {
            if(isFahrenheit){
                Text(text = "Convert to Celsius")
            }else{
                Text(text = "Convert to Fahrenheit")
            }
        }
        
    }
}

