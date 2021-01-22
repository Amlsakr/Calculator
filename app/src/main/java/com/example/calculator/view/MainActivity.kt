package com.example.calculator.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
            mainActivityBinding.secondOperandEditText.text.clear()
            mainActivityBinding.addButton.background = resources.getDrawable(R.drawable.button_normal)
            mainActivityBinding.addButton.setTextColor(resources.getColor(R.color.white))
//            mainActivityBinding.addButton.isSelected = false
//            mainActivityBinding.minusButton.isSelected = false
//            mainActivityBinding.multiplyButton.isSelected = false
//            mainActivityBinding.divideButton.isSelected = false
        })

    }

    fun equal(view: View) {
        var firstOperand =mainActivityBinding.firstOperandEditText.text.toString().toDouble()
        var secondOperand =mainActivityBinding.secondOperandEditText.text.toString().toDouble()
        calculatorViewModel.postResultToLiveData(firstOperand , secondOperand , operation)
    }

    fun getOperation(view: View) {
        when(view.id){
            R.id.addButton -> {
                operation = '+'
                mainActivityBinding.addButton.background = resources.getDrawable(R.drawable.button_pressed)
                mainActivityBinding.addButton.setTextColor(resources.getColor(R.color.black))

            }
            R.id.multiplyButton -> {
                operation = '*'
                mainActivityBinding.multiplyButton.isSelected = true
            }
            R.id.divideButton -> {
                operation = '/'
                mainActivityBinding.divideButton.isSelected = true
            }
            R.id.minusButton -> {
                operation = '-'
                mainActivityBinding.minusButton.isSelected = true
            }


        }
    }


}