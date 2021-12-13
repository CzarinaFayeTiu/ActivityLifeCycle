package com.mobdeve.s12.tiu.czarina.activitylifecycle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mobdeve.s12.tiu.czarina.activitylifecycle.databinding.ActivityFormBinding

class FormActivity : AppCompatActivity() {
    lateinit var binding: ActivityFormBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonsubmit.setOnClickListener{
            var bundle = Bundle()
            bundle.putString("name", binding.textname.text.toString())
            bundle.putString("email", binding.textemail.text.toString())

            var resultIntent = Intent()
            resultIntent.putExtras(bundle)

            setResult(RESULT_OK, resultIntent)
            finish()

        }
    }
}