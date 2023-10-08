package com.example.temperatureconverter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.lang.Exception

// this class will manage the values displayed in the user interface
class ViewModelForTemp : ViewModel() {

//    state of the toggle switch. true = Fahrenheit, false = Celsius
    var isFahrenheit by mutableStateOf(true)

//    calculated result. This will be updated when the calculation button is pressed
    var convertedTemperatures by mutableStateOf("")

//    change the state of the temperature when the switch is toggled
    fun doSwitchToggle(){
    isFahrenheit = !isFahrenheit
    }

//    calculate the temperature when the calculate button is pressed
    fun calculateConversion(inputValue:String){

        try{
            val temperature = inputValue.toDouble()

            // if  the toggle switch is set to Fahrenheit, convert to Celsius
            if (isFahrenheit){
                convertedTemperatures = "%.2f".format((temperature - 32) * 5 / 9)
                // add celsius symbol to the end of the string
                convertedTemperatures += "\u2103" // unicode for celsius symbol
            }else{
                // if the toggle switch is set to Celsius, convert to Fahrenheit
                convertedTemperatures = "%.2f".format(temperature * 9 / 5 + 32)
                // add fahrenheit symbol to the end of the string
                convertedTemperatures += "\u2109" // unicode for fahrenheit symbol
            }
        }
        catch (e:Exception){
            // if the input is not a valid number, display an error message
            convertedTemperatures = "Invalid input"
        }
    }

}