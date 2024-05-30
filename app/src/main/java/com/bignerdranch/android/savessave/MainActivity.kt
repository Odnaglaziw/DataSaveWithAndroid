package com.bignerdranch.android.savessave

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    private lateinit var shapeSpinner: Spinner
    private lateinit var input1: EditText
    private lateinit var input2: EditText
    private lateinit var input3: EditText
    private lateinit var calculateButton: Button
    private lateinit var resultTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        shapeSpinner = findViewById(R.id.shapeSpinner)
        input1 = findViewById(R.id.input1)
        input2 = findViewById(R.id.input2)
        input3 = findViewById(R.id.input3)
        calculateButton = findViewById(R.id.calculateButton)
        resultTextView = findViewById(R.id.resultTextView)

        val preferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val editor = preferences.edit()

        ArrayAdapter.createFromResource(
            this,
            R.array.shapes,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            shapeSpinner.adapter = adapter
        }

        val lastSelectedShape = preferences.getInt("last_selected_shape", 0)
        shapeSpinner.setSelection(lastSelectedShape)

        shapeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                editor.putInt("last_selected_shape", position).apply()
                when (position) {
                    0 -> { // Triangle
                        input1.hint = "Side 1"
                        input2.hint = "Side 2"
                        input3.hint = "Side 3"
                        input1.visibility = View.VISIBLE
                        input2.visibility = View.VISIBLE
                        input3.visibility = View.VISIBLE
                    }
                    1 -> { // Rectangle
                        input1.hint = "Length"
                        input2.hint = "Width"
                        input1.visibility = View.VISIBLE
                        input2.visibility = View.VISIBLE
                        input3.visibility = View.GONE
                    }
                    2 -> { // Circle
                        input1.hint = "Radius"
                        input1.visibility = View.VISIBLE
                        input2.visibility = View.GONE
                        input3.visibility = View.GONE
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        calculateButton.setOnClickListener {
            val shape = shapeSpinner.selectedItemPosition
            val result = when (shape) {
                0 -> { // Triangle
                    val side1 = input1.text.toString().toDoubleOrNull() ?: 0.0
                    val side2 = input2.text.toString().toDoubleOrNull() ?: 0.0
                    val side3 = input3.text.toString().toDoubleOrNull() ?: 0.0
                    side1 + side2 + side3
                }

                1 -> { // Rectangle
                    val length = input1.text.toString().toDoubleOrNull() ?: 0.0
                    val width = input2.text.toString().toDoubleOrNull() ?: 0.0
                    2 * (length + width)
                }

                2 -> { // Circle
                    val radius = input1.text.toString().toDoubleOrNull() ?: 0.0
                    2 * Math.PI * radius
                }

                else -> 0.0
            }
            resultTextView.text = "Perimeter: %.2f".format(result)
        }
    }
}
