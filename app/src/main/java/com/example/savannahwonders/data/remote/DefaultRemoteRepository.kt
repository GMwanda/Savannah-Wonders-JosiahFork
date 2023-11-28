package com.example.savannahwonders.data.remote

import com.example.savannahwonders.data.model.DestinationModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import javax.inject.Inject
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class DefaultRemoteRepository @Inject constructor(private val firebaseDatabase: FirebaseDatabase) : RemoteRepository {
    val destinationsRef = firebaseDatabase.getReference("Destinations")
    override suspend fun getAllDestinations(): List<DestinationModel> {
        return suspendCoroutine { continuation: Continuation<List<DestinationModel>> ->
            var destinationModelMutableList: MutableList<DestinationModel> = mutableListOf()
            destinationsRef.limitToFirst(6).addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (snap in snapshot.children) {
                        println(snap.child("Name").getValue(String::class.java))
                        val destination = DestinationModel(
                            name = snap.child("Name").getValue(String::class.java),
                            description = snap.child("Description").getValue(String::class.java),
                            location = snap.child("Location").getValue(String::class.java),
                            mainImage = snap.child("Main_Image").getValue(String::class.java),
                            otherImages = snap.child("Other_Images").getValue(String::class.java),
                            placeID = snap.child("Place ID").getValue(Int::class.java),
                            rating = snap.child("Rating").getValue(Double::class.java),
                            reviews = snap.child("Reviews").getValue(Int::class.java),
                            tripID = snap.child("Trip ID").getValue(Int::class.java)
                        )
                        println(destination.location)
                        destinationModelMutableList.add(destination)
                    }
                    continuation.resume(destinationModelMutableList)
                }
                override fun onCancelled(error: DatabaseError) {
                    println("GET ALL PRODUCTS ERROR!!!")
                    println(error.message)
                }
            })
        }
//        var destinationModelMutableList: MutableList<DestinationModel> = mutableListOf()
//        return flow {
//            val snapshot = destinationsRef.get().await()
//            for(snap in snapshot.children){
//                destinationModelMutableList.add(snap.toObject(DestinationModel::class))
//            }
//            emit(destinationModelMutableList)
//        }
    }

    override suspend fun testDb() {
        println(firebaseDatabase.reference)
    }
}