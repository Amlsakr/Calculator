package com.example.calculator.view

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calculator.R
import com.example.calculator.databinding.ActivityMainBinding
import com.example.calculator.view.adapter.HistoryAdapter
import com.example.calculator.viewModel.CalculatorViewModel

class MainActivity : AppCompatActivity()  {
    private lateinit var mainActivityBinding : ActivityMainBinding
    private val calculatorViewModel : CalculatorViewModel  by viewModels()
    private lateinit var historyAdapter: HistoryAdapter
    var numberOfUndos  = 0
    var undoArrayList  =  ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainActivityBinding.root
        setContentView(view)
        historyAdapter = HistoryAdapter()
        calculatorViewModel.postHistoryToLiveData()
        mainActivityBinding.recyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        mainActivityBinding.recyclerView.adapter = historyAdapter
        mainActivityBinding.firstOperandEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mainActivityBinding.firstOperandEditText.setSelection(mainActivityBinding.firstOperandEditText.text.toString().length);
                if (mainActivityBinding.firstOperandEditText.text.toString().length == 0) {
                    mainActivityBinding.equalButton.isEnabled = false
                }

            }

            override fun afterTextChanged(s: Editable?) {


            }

        })
        calculatorViewModel.calculationHistoryMutableLiveData.observe(this, Observer {
            if (it.size > 0) {
                historyAdapter.historyItems = it
                historyAdapter.notifyDataSetChanged()
            }
        })

        calculatorViewModel.resultMutableLiveData.observe(this, Observer<Double> { result ->
            mainActivityBinding.resultTextView.text = result.toString()
            mainActivityBinding.firstOperandEditText.text.clear()
            mainActivityBinding.equalButton.isEnabled = false
            mainActivityBinding.addButton.isSelected = false
            mainActivityBinding.minusButton.isSelected = false
            mainActivityBinding.multiplyButton.isSelected = false
            mainActivityBinding.divideButton.isSelected = false
        })



    }

    fun equal(view: View) {
        var input = mainActivityBinding.firstOperandEditText.text.toString()
        if (input.length > 0) {
            if (isExpressionCorrect(input)) {
                var firstOperand = mainActivityBinding.firstOperandEditText.text.toString()
                calculatorViewModel.postResultToLiveData(firstOperand)
                numberOfUndos = 0
                undoArrayList.clear()
            } else {
                Toast.makeText(this, "Please Enter Valid Expression", Toast.LENGTH_SHORT).show()
            }
        }else {
            Toast.makeText(this, "Please Enter  Expression", Toast.LENGTH_SHORT).show()
        }
        mainActivityBinding.equalButton.isEnabled = false
        hideKeyboard()
    }

    fun getOperation(view: View) {
        when(view.id){
            R.id.addButton -> {
                mainActivityBinding.addButton.isSelected = true
                mainActivityBinding.minusButton.isSelected = false
                mainActivityBinding.multiplyButton.isSelected = false
                mainActivityBinding.divideButton.isSelected = false
                var input = mainActivityBinding.firstOperandEditText.text.toString()
                if (input.length > 0) {
                    if (checkIfLastItemIsNumberOrOperator(input.get(input.length - 1).toString())) {
                        mainActivityBinding.firstOperandEditText.setText(input + getString(R.string.add))
                    }
                }
            }
            R.id.multiplyButton -> {
                mainActivityBinding.multiplyButton.isSelected = true
                mainActivityBinding.addButton.isSelected = false
                mainActivityBinding.minusButton.isSelected = false
                mainActivityBinding.divideButton.isSelected = false
                var input = mainActivityBinding.firstOperandEditText.text.toString()
                if (input.length > 0) {
                    if (checkIfLastItemIsNumberOrOperator(input.get(input.length - 1).toString())) {
                        mainActivityBinding.firstOperandEditText.setText(input + getString(R.string.multiply))
                    }
                }
            }
            R.id.divideButton -> {
                mainActivityBinding.divideButton.isSelected = true
                mainActivityBinding.multiplyButton.isSelected = false
                mainActivityBinding.addButton.isSelected = false
                mainActivityBinding.minusButton.isSelected = false
                var input = mainActivityBinding.firstOperandEditText.text.toString()
                if (input.length > 0) {
                    if (checkIfLastItemIsNumberOrOperator(input.get(input.length - 1).toString())) {
                        mainActivityBinding.firstOperandEditText.setText(input + getString(R.string.divide))
                    }
                }
            }
            R.id.minusButton -> {
                mainActivityBinding.minusButton.isSelected = true
                mainActivityBinding.divideButton.isSelected = false
                mainActivityBinding.multiplyButton.isSelected = false
                mainActivityBinding.addButton.isSelected = false
                var input = mainActivityBinding.firstOperandEditText.text.toString()
                if (input.length > 0) {
                    if (checkIfLastItemIsNumberOrOperator(input.get(input.length - 1).toString())) {
                        mainActivityBinding.firstOperandEditText.setText(input + getString(R.string.minus))
                    }
                }
            }
        }
        mainActivityBinding.equalButton.isEnabled = true

    }


    fun hideKeyboard(){
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(mainActivityBinding.firstOperandEditText.windowToken, 0)

    }
    fun checkIfLastItemIsNumberOrOperator(s: String) : Boolean{
        if(s == "+" || s == "-"
                    || s== "*" || s == "/"){
                return false
            }
        return true
    }



    fun onClearClick(view: View) {
        mainActivityBinding.firstOperandEditText.text.clear()
        numberOfUndos = 0
        undoArrayList.clear()
    }

    fun onBracketClick(view: View) {
        val button: AppCompatButton = view as AppCompatButton
        var input = mainActivityBinding.firstOperandEditText.text.toString()
        mainActivityBinding.firstOperandEditText.setText(input + button.text)
    }

    fun isExpressionCorrect(expression: String): Boolean {
        return expression.matches("(([()]*[+*×÷/-])?[()]*(\\d+(\\.\\d+)?)[()]*)+".toRegex())
    }

    fun undo(view: View) {
        var input = mainActivityBinding.firstOperandEditText.text.toString()
        if (isExpressionCorrect(input) && input.length > 2){
            mainActivityBinding.firstOperandEditText.setText(input.substring(0 ,input.length-2))
            numberOfUndos++
            undoArrayList.add(input.substring(input.length-2))
        }
    }
    fun redo(view: View) {
        var input = mainActivityBinding.firstOperandEditText.text.toString()
        if (numberOfUndos > 0){
            numberOfUndos--
            mainActivityBinding.firstOperandEditText.setText(input + undoArrayList.get(undoArrayList.size - 1))
            undoArrayList.removeAt(undoArrayList.size - 1)
        }
    }

}


