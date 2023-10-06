package com.example.savannahwonders.data.di

import com.example.savannahwonders.data.auth.AuthRepository
import com.example.savannahwonders.data.auth.DefaultAuthRespository
import com.google.firebase.auth.FirebaseAuth
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
    fun providesDefaultRepo(firebaseAuth: FirebaseAuth) :AuthRepository{
        return DefaultAuthRespository(firebaseAuth)
    }
}