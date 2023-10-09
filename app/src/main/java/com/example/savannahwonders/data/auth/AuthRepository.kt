package com.example.savannahwonders.data.auth

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


interface AuthRepository {
    fun loginUser(email: String, password: String) : Flow<Resource<AuthResult>>
    fun registerUser(email: String, password: String, name: String) : Flow<Resource<AuthResult>>
    fun logOut()
}

class DefaultAuthRespository @Inject constructor(private val firebaseAuth: FirebaseAuth, private val database: FirebaseDatabase): AuthRepository {
    val userTableRef = database.getReference("Users")
    override fun loginUser(email: String, password: String): Flow<Resource<AuthResult>> {
        return flow {
            emit(Resource.Loading())
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            emit(Resource.Success(result))
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }

    override fun registerUser(email: String, password: String, name: String): Flow<Resource<AuthResult>> {
        return flow {
            emit(Resource.Loading())
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            emit(Resource.Success(result))
            var userId = firebaseAuth.currentUser?.uid
            if (userId != null) {
                userTableRef.child(userId).child("Name").setValue(name)
                userTableRef.child(userId).child("Email").setValue(email)
            }
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }
    override fun logOut() {
        firebaseAuth.signOut()
    }

}