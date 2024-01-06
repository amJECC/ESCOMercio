package com.example.escomercio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashScreen : AppCompatActivity() {

    lateinit var handler: Handler


    override fun onCreate(savedInstanceState: Bundle?) {

        setTheme(R.style.SplashTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        handler = Handler()
        handler.postDelayed({
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
            finish()
        },1500)




    }
}