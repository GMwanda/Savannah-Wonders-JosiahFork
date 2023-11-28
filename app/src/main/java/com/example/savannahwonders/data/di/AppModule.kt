package com.example.savannahwonders.data.di

import com.example.savannahwonders.data.auth.AuthRepository
import com.example.savannahwonders.data.auth.DefaultAuthRespository
import com.example.savannahwonders.data.remote.DefaultRemoteRepository
import com.example.savannahwonders.data.remote.RemoteRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providesFirebaseRealtimeDatabase() = Firebase.database

    @Provides
    @Singleton
    fun providesDefaultRepo(firebaseAuth: FirebaseAuth, database: FirebaseDatabase) :AuthRepository{
        return DefaultAuthRespository(firebaseAuth, database)
    }

    @Provides
    fun providesDefaultRemoteRepo(database: FirebaseDatabase) : RemoteRepository{
        return DefaultRemoteRepository(database)
    }

}