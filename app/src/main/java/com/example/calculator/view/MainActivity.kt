package com.example.calculator.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.calculator.R
import com.example.calculator.databinding.ActivityMainBinding
import com.example.calculator.viewModel.CalculatorViewModel

class MainActivity : AppCompatActivity()  {
    private lateinit var mainActivityBinding : ActivityMainBinding
    private lateinit var calculatorViewModel : CalculatorViewModel
    var operation = ' '
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainActivityBinding.root
        setContentView(view)

        calculatorViewModel = ViewModelProvider(this).get(CalculatorViewModel::class.java)
        calculatorViewModel.resultMutableLiveData.observe(this , Observer<Double>{
            result-> mainActivityBinding.resultTextView.text = result.toString()
        })

    }

    fun equal(view: View) {
        var firstOperand =mainActivityBinding.firstOperandEditText.text.toString().toDouble()
        var secondOperand =mainActivityBinding.secondOperandEditText.text.toString().toDouble()
        calculatorViewModel.calculateResult(firstOperand , secondOperand , operation)
    }
    fun getOperation(view: View) {
        when(view.id){
            R.id.addButton -> operation = '+'
            R.id.multiplyButton -> operation = '*'
            R.id.divideButton -> operation = '/'
            R.id.minusButton -> operation = '-'

        }
    }


}