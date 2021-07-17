package dev.sourabh.madscalculator.android.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import dev.sourabh.madscalculator.android.models.User

class LoginActivityRepository {

    private val userAuthLiveData = MutableLiveData<Boolean>()

    fun loginUser(email: String, password: String) {
        try {
            val firebaseDatabaseReference = FirebaseDatabase.getInstance("https://madscalculator-12d5f-default-rtdb.asia-southeast1.firebasedatabase.app/").reference
            firebaseDatabaseReference.child("login").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val user = dataSnapshot.getValue(User::class.java)
                    user?.let {
                        if (email == user.email && user.password == password) {
                            userAuthLiveData.postValue(true)
                        } else {
                            userAuthLiveData.postValue(false)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    userAuthLiveData.postValue(false)
                }
            })
        } catch (e: Exception) {
            userAuthLiveData.postValue(false)
        }
    }

    fun getUserAuthLiveData(): LiveData<Boolean> = userAuthLiveData
}