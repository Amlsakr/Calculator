package com.example.calculator

import android.icu.util.TimeUnit
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.example.calculator.viewModel.CalculatorViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class CalculatorTest {
    @get:Rule
    val testInstantTaskExecutorRule= InstantTaskExecutorRule()

    private  var calculatorViewModel : CalculatorViewModel = CalculatorViewModel()

    @Before
    fun initializeViewMidel (){

    }
    @Test
    fun testEqual (){
     calculatorViewModel.postResultToLiveData(4.0, 7.0, '+')
        var result =  calculatorViewModel.resultMutableLiveData.getOrAwaitValue()
        assertEquals(result, 11.0 , 0.0 )
    }


}