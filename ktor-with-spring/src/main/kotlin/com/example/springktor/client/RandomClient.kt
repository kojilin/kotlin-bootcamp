package com.example.springktor.client

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomClient {
    @GET("api/random/")
    fun getRandom(@Query("n") n: Int): Single<ResponseBody>
}
