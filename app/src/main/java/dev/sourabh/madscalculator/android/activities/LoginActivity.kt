package dev.sourabh.madscalculator.android.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.sourabh.madscalculator.android.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_MADSCalculator)
        setContentView(R.layout.activity_login)
    }
}