package com.example.calculator.view

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calculator.R
import com.example.calculator.databinding.ActivityMainBinding
import com.example.calculator.view.adapter.HistoryAdapter
import com.example.calculator.viewModel.CalculatorViewModel

class MainActivity : AppCompatActivity()  {
    private lateinit var mainActivityBinding : ActivityMainBinding
    private val calculatorViewModel : CalculatorViewModel  by viewModels()
    private lateinit var historyAdapter: HistoryAdapter
    var operation = ' '
    var index = 0
    var operationList = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainActivityBinding.root
        setContentView(view)
        historyAdapter = HistoryAdapter()
        calculatorViewModel.postHistoryToLiveData()
        mainActivityBinding.recyclerView.layoutManager = LinearLayoutManager(this ,LinearLayoutManager.VERTICAL ,false)
        mainActivityBinding.recyclerView.adapter = historyAdapter
        calculatorViewModel.calculationHistoryMutableLiveData.observe(this , Observer {
            if(it.size > 0){
              historyAdapter.historyItems = it
                historyAdapter.notifyDataSetChanged()
            }
        })

        calculatorViewModel.resultMutableLiveData.observe(this , Observer<Double>{
            result-> mainActivityBinding.resultTextView.text = result.toString()
            mainActivityBinding.firstOperandEditText.text.clear()
            mainActivityBinding.equalButton.isEnabled = false
            mainActivityBinding.addButton.background = resources.getDrawable(R.drawable.button_normal)
            mainActivityBinding.addButton.setTextColor(resources.getColor(R.color.white))
//            mainActivityBinding.addButton.isSelected = false
//            mainActivityBinding.minusButton.isSelected = false
//            mainActivityBinding.multiplyButton.isSelected = false
//            mainActivityBinding.divideButton.isSelected = false
        })

    }

    fun equal(view: View) {
        if (mainActivityBinding.firstOperandEditText.text.toString().length > 0 ) {
            var firstOperand = mainActivityBinding.firstOperandEditText.text.toString().toDouble()
            calculatorViewModel.postResultToLiveData(firstOperand, 0.0, operation)
        }else {
            Toast.makeText(this , "Please Enter Operand" , Toast.LENGTH_SHORT).show()
        }
        mainActivityBinding.equalButton.isEnabled = false
        hideKeyboard()

    }

    fun getOperation(view: View) {
        when(view.id){
            R.id.addButton -> {
                operation = '+'
                mainActivityBinding.addButton.isSelected = true
                var input = mainActivityBinding.firstOperandEditText.text.toString()
                mainActivityBinding.firstOperandEditText.setText( input + "+")


            }
            R.id.multiplyButton -> {
                operation = '*'
                mainActivityBinding.multiplyButton.isSelected = true
                var input = mainActivityBinding.firstOperandEditText.text.toString()
                mainActivityBinding.firstOperandEditText.setText( input + "*")
            }
            R.id.divideButton -> {
                operation = '/'
                mainActivityBinding.divideButton.isSelected = true
                var input = mainActivityBinding.firstOperandEditText.text.toString()
                mainActivityBinding.firstOperandEditText.setText( input + "/")
            }
            R.id.minusButton -> {
                operation = '-'
                mainActivityBinding.minusButton.isSelected = true
                var input = mainActivityBinding.firstOperandEditText.text.toString()
                mainActivityBinding.firstOperandEditText.setText( input + "-")
            }


        }
        mainActivityBinding.equalButton.isEnabled = true
        hideKeyboard()
    }


    fun hideKeyboard(){
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(mainActivityBinding.firstOperandEditText.windowToken, 0)

    }


}