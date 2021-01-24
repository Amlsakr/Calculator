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

    private var evaluateString = EvaluateString()


    @Test
    fun testEqual (){
        var result = evaluateString.evaluate("2+3+5")
        assertEquals(result, 10.0 , 0.0 )
    }


}