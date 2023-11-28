package com.example.savannahwonders.data.remote

import com.example.savannahwonders.data.model.DestinationModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class DefaultRemoteRepository @Inject constructor(private val firebaseDatabase: FirebaseDatabase) : RemoteRepository {
    private var userId = FirebaseAuth.getInstance().currentUser?.uid
    private val destinationsRef = firebaseDatabase.getReference("Destinations")
    private val favoritesRef = firebaseDatabase.getReference("Favorites")
    override suspend fun getAllDestinations(): List<DestinationModel> {
        return suspendCoroutine { continuation: Continuation<List<DestinationModel>> ->
            var destinationModelMutableList: MutableList<DestinationModel> = mutableListOf()
            destinationsRef.limitToFirst(6).addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (snap in snapshot.children) {
//                        println(snap.child("Name").getValue(String::class.java))
                        println(snap.key)
                        val destination = DestinationModel(
                            name = snap.child("Name").getValue(String::class.java),
                            description = snap.child("Description").getValue(String::class.java),
                            location = snap.child("Location").getValue(String::class.java),
                            mainImage = snap.child("Main_Image").getValue(String::class.java),
                            otherImages = snap.child("Other_Images").getValue(String::class.java),
                            placeID = snap.key,
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
    }

    override suspend fun getFavorites(): List<DestinationModel> {
        val favoriteDestinationList: MutableList<DestinationModel> = mutableListOf()
        val snapshot = favoritesRef.get().await()
        for (snap in snapshot.children){
            if (snap.child("userId").value == userId){
                val favoriteDestination = DestinationModel(
                    name = snap.child("name").getValue(String::class.java),
                    description = snap.child("description").getValue(String::class.java),
                    location = snap.child("location").getValue(String::class.java),
                    mainImage = snap.child("mainImage").getValue(String::class.java),
                    otherImages = snap.child("otherImages").getValue(String::class.java),
                    placeID = snap.child("placeID").getValue(String::class.java),
                    rating = snap.child("rating").getValue(Double::class.java),
                    reviews = snap.child("reviews").getValue(Int::class.java),
                    tripID = snap.child("tripID").getValue(Int::class.java)
                )
                println("Destination ${favoriteDestination} retrieved from favorites")
                favoriteDestinationList.add(favoriteDestination)
            }
        }
        return favoriteDestinationList
    }

    override suspend fun addToFavorites(destinationModel: DestinationModel) {
        val id = "${destinationModel.placeID}$userId"
        favoritesRef.child(id).setValue(destinationModel)
        favoritesRef.child(id).child("userId").setValue(userId)
        println("Destination ${destinationModel.name} added to favorites")
    }

    override suspend fun search(searchTerm: String): List<DestinationModel> {
        return suspendCoroutine { continuation: Continuation<List<DestinationModel>> ->
            var searchResult: MutableList<DestinationModel> = mutableListOf()
            destinationsRef.addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (snap in snapshot.children){
                        if(snap.child("Name").value.toString().contains(searchTerm) || snap.child("Description").value.toString().contains(searchTerm)){
                            val destination = DestinationModel(
                                name = snap.child("Name").getValue(String::class.java),
                                description = snap.child("Description").getValue(String::class.java),
                                location = snap.child("Location").getValue(String::class.java),
                                mainImage = snap.child("Main_Image").getValue(String::class.java),
                                otherImages = snap.child("Other_Images").getValue(String::class.java),
                                placeID = snap.key,
                                rating = snap.child("Rating").getValue(Double::class.java),
                                reviews = snap.child("Reviews").getValue(Int::class.java),
                                tripID = snap.child("Trip ID").getValue(Int::class.java)
                            )
                            searchResult.add(destination)
                        }
                    }
                    continuation.resume(searchResult)
                }
                override fun onCancelled(error: DatabaseError) {
                    println("SEARCH ERROR!!!")
                    println(error.message)
                }
            })
        }
    }
}