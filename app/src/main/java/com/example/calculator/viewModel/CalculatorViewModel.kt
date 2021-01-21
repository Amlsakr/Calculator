package com.example.calculator.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel()  {
    var resultMutableLiveData : MutableLiveData<Double> = MutableLiveData()

    fun calculateResult (firstOperand : Double , secondOperand : Double , operation : Char) {
        var result = 0.0
        when(operation){
            '+' -> result = firstOperand + secondOperand
            '-' -> result = firstOperand - secondOperand
            '*' -> result = firstOperand * secondOperand
            '/' -> result = firstOperand / secondOperand

        }
        resultMutableLiveData.postValue(result)

    }
}