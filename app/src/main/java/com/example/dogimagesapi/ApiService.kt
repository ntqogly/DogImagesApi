package com.example.dogimagesapi

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface ApiService {

    @GET("random")
    fun loadDogImage(): Single<DogImage>
}