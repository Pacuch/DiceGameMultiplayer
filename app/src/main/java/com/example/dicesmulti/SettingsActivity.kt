package com.example.dicesmulti

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup


class SettingsActivity : AppCompatActivity() {
    private var rbId: Int = 0
    private fun toggle(){
        val radioGroupThrow = findViewById<RadioGroup>(R.id.radioGroupThrow)
        radioGroupThrow.setOnCheckedChangeListener{radioGroupThrow, id->valueSet(id)}
    }

    private fun valueSet(id: Int) {
        val checked = findViewById<RadioButton>(id).isChecked
        when (id) {
            R.id.throwOne ->
                if (checked) {
                    rbId = 0
                }
            R.id.throwTwo ->
                if (checked) {
                    rbId = 1
                }
            R.id.throwThree ->
                if (checked) {
                    rbId = 2
                }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        toggle()
        val applyButton = findViewById<Button>(R.id.applyButtonDices)

        applyButton.setOnClickListener{
            applyChanges()
            finish()
        }
    }
    private fun applyChanges() {
        val intent = Intent()
        intent.putExtra("numberOfDices", rbId)
        setResult(RESULT_OK, intent)
    }
}

