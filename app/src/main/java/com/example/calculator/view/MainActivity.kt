package com.example.calculator.view

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.Selection
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainActivityBinding.root
        setContentView(view)
        historyAdapter = HistoryAdapter()
        calculatorViewModel.postHistoryToLiveData()
        mainActivityBinding.recyclerView.layoutManager = LinearLayoutManager(this ,LinearLayoutManager.VERTICAL ,false)
        mainActivityBinding.recyclerView.adapter = historyAdapter
        mainActivityBinding.firstOperandEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.e("et", s.toString() + "start" + start + "before" + before + "count" + count)
                mainActivityBinding.firstOperandEditText.setSelection(mainActivityBinding.firstOperandEditText.text.toString().length);

            }

            override fun afterTextChanged(s: Editable?) {


            }

        })
        calculatorViewModel.calculationHistoryMutableLiveData.observe(this , Observer {
            if(it.size > 0){
              historyAdapter.historyItems = it
                historyAdapter.notifyDataSetChanged()
            }
        })

        calculatorViewModel.resultMutableLiveData.observe(this , Observer<Int>{
            result-> mainActivityBinding.resultTextView.text = result.toString()
            mainActivityBinding.firstOperandEditText.text.clear()
            mainActivityBinding.equalButton.isEnabled = false
            mainActivityBinding.addButton.isSelected = false
            mainActivityBinding.minusButton.isSelected = false
            mainActivityBinding.multiplyButton.isSelected = false
            mainActivityBinding.divideButton.isSelected = false
        })



    }

    fun equal(view: View) {
        if (mainActivityBinding.firstOperandEditText.text.toString().length > 0 ) {
            var firstOperand = mainActivityBinding.firstOperandEditText.text.toString()
            calculatorViewModel.postResultToLiveData(firstOperand)
        }else {
            Toast.makeText(this , "Please Enter Operand" , Toast.LENGTH_SHORT).show()
        }
        mainActivityBinding.equalButton.isEnabled = false
        hideKeyboard()

    }

    fun getOperation(view: View) {
        when(view.id){
            R.id.addButton -> {
                mainActivityBinding.addButton.isSelected = true
                    if (checkIfLastItemIsNumberOrOperator(mainActivityBinding.firstOperandEditText.text.toString().
                            get(mainActivityBinding.firstOperandEditText.text.toString().length - 1).toString())) {
                        var input = mainActivityBinding.firstOperandEditText.text.toString()
                        mainActivityBinding.firstOperandEditText.setText(input + getString(R.string.add))
                }
            }
            R.id.multiplyButton -> {

                mainActivityBinding.multiplyButton.isSelected = true
                if (checkIfLastItemIsNumberOrOperator(mainActivityBinding.firstOperandEditText.text.toString()
                                .get(mainActivityBinding.firstOperandEditText.text.toString().length - 1).toString())) {
                    var input = mainActivityBinding.firstOperandEditText.text.toString()
                    mainActivityBinding.firstOperandEditText.setText(input + getString(R.string.multiply))

                }
            }
            R.id.divideButton -> {
                mainActivityBinding.divideButton.isSelected = true

                if (checkIfLastItemIsNumberOrOperator(mainActivityBinding.firstOperandEditText.text.toString()
                                .get(mainActivityBinding.firstOperandEditText.text.toString().length - 1).toString())) {
                        var input = mainActivityBinding.firstOperandEditText.text.toString()
                        mainActivityBinding.firstOperandEditText.setText(input + getString(R.string.divide))
                }
            }
            R.id.minusButton -> {
                mainActivityBinding.minusButton.isSelected = true
                if (checkIfLastItemIsNumberOrOperator(mainActivityBinding.firstOperandEditText.text.toString()
                                .get(mainActivityBinding.firstOperandEditText.text.toString().length - 1).toString())) {
                        var input = mainActivityBinding.firstOperandEditText.text.toString()
                        mainActivityBinding.firstOperandEditText.setText(input + getString(R.string.minus))

               }
            }
        }
        mainActivityBinding.equalButton.isEnabled = true

    }


    fun hideKeyboard(){
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(mainActivityBinding.firstOperandEditText.windowToken, 0)

    }
    fun checkIfLastItemIsNumberOrOperator(s:String) : Boolean{
        if(s == "+" || s == "-"
                    || s== "*" || s == "/"){
                return false
            }


        return true
    }


}


