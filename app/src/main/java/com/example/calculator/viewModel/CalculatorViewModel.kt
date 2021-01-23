package com.example.calculator.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalculatorViewModel(application: Application) : AndroidViewModel(application)  {
    var resultMutableLiveData : MutableLiveData<Int> = MutableLiveData()
    var calculationHistoryMutableLiveData : MutableLiveData<ArrayList<String>> = MutableLiveData()
    var evaluateString = EvaluateString()

    val sharedPreferenceKey = "CalculatorHistory"
    val calculatorHistoryList = "calculatorHistoryList"
    var sharedpreferences = application.getSharedPreferences(sharedPreferenceKey, Context.MODE_PRIVATE)
    var editor = sharedpreferences.edit();


    fun postResultToLiveData (expression :String) : MutableLiveData<Int>{
        var result = evaluateString.evaluate(expression)
        var list = reterieveListFromSharedPreference()
        list.add(expression + " = "+ result.toString())
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