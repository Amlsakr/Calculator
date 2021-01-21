package com.example.calculator

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
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
     var result =  calculatorViewModel.calculateResult(4.0 , 7.0, '+')

        assertEquals(result, 11.0 , 0.0)
    }

}