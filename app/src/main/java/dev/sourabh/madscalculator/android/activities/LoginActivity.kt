package dev.sourabh.madscalculator.android.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import dev.sourabh.madscalculator.android.R
import dev.sourabh.madscalculator.android.databinding.ActivityLoginBinding
import dev.sourabh.madscalculator.android.viewmodels.CalculatorActivityViewModel
import dev.sourabh.madscalculator.android.viewmodels.LoginActivityViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginActivityViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_MADSCalculator)
        binding = ActivityLoginBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(LoginActivityViewModel::class.java)

        initEditTextListeners()
        initLoginButtonState()
        initUI()
        login()
    }

    private fun login() {
        viewModel.userAuthLiveData().observe(this, {areLoginCredentialsCorrect ->
            Log.d("XXX", "TRGG")
            binding.progressBar.visibility = View.GONE
            if(areLoginCredentialsCorrect){
                goToCalculatorActivity()
            }else{
                Toast.makeText(this, "Please enter correct credentials", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initUI() {
        binding.button.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            viewModel.login()
        }
    }

    private fun goToCalculatorActivity() {
        startActivity(Intent(this, CalculatorActivity::class.java))
        finish()
    }

    private fun initLoginButtonState() {
        lifecycleScope.launch {
            viewModel.isLoginEnabled.collect { areDetailsValid ->
                binding.button.isEnabled = areDetailsValid
            }
        }
    }

    private fun initEditTextListeners() {
        with(binding){
            editTextTextEmailAddress.addTextChangedListener {
                viewModel.setEmailAddress(it.toString())
            }
            editTextTextPassword.addTextChangedListener {
                viewModel.setPassword(it.toString())
            }
        }
    }
}