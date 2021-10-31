package com.example.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged

class ComputedActivity : AppCompatActivity(){

    private lateinit var nbr1: EditText;
    private lateinit var nbr2: EditText;
    private var resultCalcul = 0
    private lateinit var buttonCalc : Button;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.compute_activity)

        initButton()
        numbers()
        buttonCalcClick()

    }

    fun numbers(){
        nbr1 = findViewById(R.id.field_1);
        nbr2 = findViewById(R.id.field_2);

        nbr1.doAfterTextChanged {
            buttonCalcClick()
        }

        nbr2.doAfterTextChanged {
            buttonCalcClick()
        }
    }

    fun getStringOfEditText(edit: EditText): String{
        return edit.text.toString().trim()
        //.trim() enleve les espaces avant et apres la string
    }

    fun getIntOfEditText(edit: EditText): Int{
        return getStringOfEditText(edit).toInt();
    }

    fun sum(){
        resultCalcul = getIntOfEditText(nbr1) + getIntOfEditText(nbr2)
    }

    fun buttonCalcClick(){
        buttonCalc.isEnabled = !getStringOfEditText(nbr1).isEmpty() && !getStringOfEditText(nbr2).isEmpty()
    }

    fun initButton(){
        buttonCalc = findViewById(R.id.btn_calculer);
        buttonCalc.setOnClickListener {
            sum()
        }
        //rajouter la textView pour faire apparaitre le resultat
    }


}