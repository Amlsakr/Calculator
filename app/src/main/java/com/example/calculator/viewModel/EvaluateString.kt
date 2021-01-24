package com.example.calculator.viewModel

import java.util.*
import java.util.regex.Pattern

class EvaluateString {
    private val parseExpression = "([()])|(\\d+(\\.\\d+)?)|([+*×÷/-])"
    private var expression: String = ""

    class MathExpressionException(message: String?) : Exception(message)


    @Throws(MathExpressionException::class)
    fun evaluate(expression: String): Double {
        this.expression = expression
        val tokens = parseTokens(expression)
        val postfix = convertToPostFix(tokens)
        val result = computeResult(postfix)
        return result
    }

    fun computeResult(queue: Queue<String>): Double {

        val stack = Stack<String>()
        while (queue.isNotEmpty()) {
            val token = queue.poll()
            if (isNumber(token)) {
                stack.push(token)
            } else if (isOperation(token)) {
                val firs = stack.pop() ?: "0.0"
                var second = "0.0"
                if (stack.isNotEmpty())
                    second = stack.pop()
                val result = calculate(firs, second, token)
                stack.add(result.toString())
            }
        }

        val pop = stack.pop().toDouble()
        val result = String.format("%.6f", pop)
        return result.toDouble()
    }

    private fun calculate(firs: String, second: String, operation: String?): Double {
        return when (operation) {
            "+" -> firs.toDouble() + second.toDouble()
            "-" -> second.toDouble() - firs.toDouble()
            "*", "×" -> firs.toDouble() * second.toDouble()
            "/", "÷" -> second.toDouble() / firs.toDouble()
            else -> 0.0
        }

    }

    @Throws(MathExpressionException::class)
    fun convertToPostFix(tokens: List<String>): Queue<String> {
        val stack = Stack<String>()
        val queue: Queue<String> = LinkedList()

        for (token in tokens) {
            when {
                isNumber(token) -> {
                    queue.add(token)
                }
                isOperation(token) -> {
                    if (stack.isNotEmpty()) {
                        val top = stack.peek()
                        if (hasLowPriority(token, top)) {
                            while (stack.isNotEmpty() && stack.peek() != "(") {
                                queue.add(stack.pop())
                            }
                        }
                    }
                    stack.push(token)

                }
                token == "(" -> {
                    stack.push(token)
                }
                token == ")" -> {
                    if (stack.isEmpty())
                        fail(token)
                    while (stack.peek() != "(") {
                        queue.add(stack.pop())
                        if (stack.isEmpty())
                            fail(token)
                    }
                    if (stack.peek() != "(") {
                        fail(token)
                    }
                    stack.pop() // remove the opened bracket
                }
            }

        }

        while (stack.isNotEmpty()) {
            queue.add(stack.pop())
        }
        return queue
    }


    private fun fail(expression: String) {
        throw MathExpressionException(" Brackets mismatch error at \'$expression\'")
    }

    private fun hasLowPriority(expression: String, top: String?): Boolean {
        if (top == null)
            return false
        val priority = listOf("*", "×", "/", "÷", "-", "+")
        return priority.indexOf(top) < priority.indexOf(expression)
    }

    private fun isOperation(expression: String?): Boolean {
        if (expression == null)
            return false
        return expression.matches("[+*/×÷-]".toRegex())
    }

    private fun isNumber(expression: String?): Boolean {
        if (expression == null)
            return false
        return expression.matches("(\\d+(\\.\\d+)?)".toRegex())
    }

    fun parseTokens(expression: String): List<String> {
        val pattern = Pattern.compile(parseExpression)
        val matcher = pattern.matcher(expression)
        val tokens = ArrayList<String>()

        while (matcher.find()) {
            tokens.add(matcher.group())
        }
        return tokens
    }



}