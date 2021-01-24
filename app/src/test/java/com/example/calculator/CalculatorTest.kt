package com.example.calculator

import android.icu.util.TimeUnit
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.example.calculator.viewModel.CalculatorViewModel
import com.example.calculator.viewModel.EvaluateString
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class CalculatorTest {
    @get:Rule
    val testInstantTaskExecutorRule= InstantTaskExecutorRule()

  //  private  var calculatorViewModel : CalculatorViewModel = CalculatorViewModel()
    private var evaluateString = EvaluateString()

    @Before
    fun initializeViewMidel (){

    }
    @Test
    fun testEqual (){
//     calculatorViewModel.postResultToLiveData(4.0, 7.0, '+')
//        var result =  calculatorViewModel.resultMutableLiveData.getOrAwaitValue()
        var result = evaluateString.evaluate("2+3+5")
        assertEquals(result, 10.0 , 0.0 )
    }


}