package dev.sourabh.madscalculator.android.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dev.sourabh.madscalculator.android.models.Operation

class CalculatorActivityRepository {

    private val operationsLiveData = MutableLiveData<List<Operation>>()

    fun saveOperationToDatabase(operationToSave: Operation) {
        val firebaseDatabaseReference =
            FirebaseDatabase.getInstance("https://madscalculator-12d5f-default-rtdb.asia-southeast1.firebasedatabase.app/").reference
        firebaseDatabaseReference.child("operations").child("user1").push()
            .setValue(operationToSave)
    }

    fun getPreviousOperations() {
        val firebaseDatabaseReference =
            FirebaseDatabase.getInstance("https://madscalculator-12d5f-default-rtdb.asia-southeast1.firebasedatabase.app/").reference
        firebaseDatabaseReference.child("operations").child("user1")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val updatedData = mutableListOf<Operation>()
                    for (operation in dataSnapshot.children) {
                        val operation = operation.getValue(Operation::class.java)
                        operation?.let {
                            updatedData.add(operation)
                        }
                    }

                    operationsLiveData.postValue(updatedData)
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }

    fun getNewOperationsLiveData() = operationsLiveData
}