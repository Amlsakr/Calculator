package com.example.calculator.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalculatorViewModel(application: Application) : AndroidViewModel(application)  {
    var resultMutableLiveData : MutableLiveData<Double> = MutableLiveData()
    var calculationHistoryMutableLiveData : MutableLiveData<ArrayList<String>> = MutableLiveData()

    val sharedPreferenceKey = "CalculatorHistory"
    val calculatorHistoryList = "calculatorHistoryList"
    var sharedpreferences = application.getSharedPreferences(sharedPreferenceKey, Context.MODE_PRIVATE)
    var editor = sharedpreferences.edit();


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

    fun postResultToLiveData (firstOperand : Double , secondOperand : Double , operation : Char) : MutableLiveData<Double>{
        var result = calculateResult(firstOperand,secondOperand,operation)
        var list = reterieveListFromSharedPreference()
        list.add(firstOperand.toString() + " " + operation + " "+ secondOperand.toString() + " = "+ result.toString())
        var set = HashSet<String>()
        set.addAll(list)
        editor.putStringSet(calculatorHistoryList,set)
        editor.apply()
        resultMutableLiveData.postValue(result)
        calculationHistoryMutableLiveData.postValue(list)
        return resultMutableLiveData
    }

    fun reterieveListFromSharedPreference () : ArrayList<String>{
        var set = sharedpreferences.getStringSet(calculatorHistoryList ,null)
        var list = ArrayList<String>()
        if (set!= null){
            list = ArrayList(set)
        }
        return list

    }

    fun postHistoryToLiveData (){
        var list = reterieveListFromSharedPreference()
        calculationHistoryMutableLiveData.postValue(list)
    }
}