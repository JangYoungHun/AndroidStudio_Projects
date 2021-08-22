package com.example.intent_practice

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import java.net.URI

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button).setOnClickListener{
            var tellIntent = Uri.parse(  "geo:0,0?q=korea,+서울,+경복궁").let {
                Intent(Intent.ACTION_VIEW, it)
            }
            startActivity(tellIntent)
        }

    }
}