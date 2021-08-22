package com.example.fragment1_practice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var launcher:ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



//        button.setOnClickListener{
//           var v = layoutInflater.inflate(R.layout.fragment1, container,false)
//            container.addView(v)
//        }


        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), ActivityResultCallback(){
            if(it.resultCode == RESULT_OK) {
                Toast.makeText(applicationContext, "OK", Toast.LENGTH_SHORT).show()
            }
        })



        button.setOnClickListener{

            var intent = Intent(this,MenuActivity::class.java)
            launcher.launch(intent)
        }
    }
}