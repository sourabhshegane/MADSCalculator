package dev.sourabh.madscalculator.android.viewmodels

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.sourabh.madscalculator.android.repository.LoginActivityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine


class LoginActivityViewModel: ViewModel() {

    private val _emailAddress = MutableStateFlow("")
    private val _password = MutableStateFlow("")
    private val repository = LoginActivityRepository()

    fun setEmailAddress(enteredEmailAddress: String){
        _emailAddress.value = enteredEmailAddress
    }

    fun setPassword(enteredPassword: String){
        _password.value = enteredPassword
    }

    val isLoginEnabled: Flow<Boolean> = combine(_emailAddress, _password) { emailAddress, password ->
        val isEmailAddressCorrect = Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()
        val isPasswordCorrect = password.isNotEmpty()
        return@combine isEmailAddressCorrect and isPasswordCorrect
    }

    fun login() = repository.loginUser(
        _emailAddress.value,
        _password.value
    )

    fun userAuthLiveData() = repository.getUserAuthLiveData()

}