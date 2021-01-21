package com.example.calculator.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel()  {
    var resultMutableLiveData : MutableLiveData<Double> = MutableLiveData()

    fun calculateResult (firstOperand : Double , secondOperand : Double , operation : Char) : Double {
        var result = 0.0
        when(operation){
            '+' -> result = firstOperand + secondOperand
            '-' -> result = firstOperand - secondOperand
            '*' -> result = firstOperand * secondOperand
            '/' -> result = firstOperand / secondOperand

        }

        return result
    }

    fun postResultToLiveData (firstOperand : Double , secondOperand : Double , operation : Char){
        var result = calculateResult(firstOperand,secondOperand,operation)
        resultMutableLiveData.postValue(result)
    }
}