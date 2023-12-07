package com.bignerdranch.android.wanderword.api

import retrofit2.http.GET
interface MapsApi {
    @GET("/")
    suspend fun fetchContents(): String
}