package com.example.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {


    private lateinit var clickButton: Button;
    private var nbClick = 0
    private lateinit var displayText: TextView;
    private lateinit var clickCalcul: Button;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonClick();
        text();
        buttonCalcul();

    }

    fun buttonClick(){
        clickButton = findViewById(R.id.btn_click_me)
        clickButton.setOnClickListener{
            nbClick++
            displayText()
            if(nbClick < 5){
                clickButton.isClickable = true
            }
            else{
                clickButton.isClickable = false
                clickButton.setAlpha(.5f);
            }

        }

    }

    fun text(){
        displayText = findViewById(R.id.text_view_id)
        displayText()
    }

    fun displayText(){

        if(nbClick == 0) {
            displayText.setVisibility(View.INVISIBLE)
        }
        else displayText.setVisibility(View.VISIBLE)

        val text = "Tu m'as cliqué $nbClick fois"
        displayText.text = text
    }

    fun buttonCalcul(){
        clickCalcul = findViewById(R.id.btn_compute)
        clickCalcul.setOnClickListener {
            val intent = Intent(baseContext, ComputedActivity::class.java)
            startActivity(intent);
        }
    }




}