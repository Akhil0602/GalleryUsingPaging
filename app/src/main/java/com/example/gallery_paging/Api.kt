package com.example.gallery_paging

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Headers
import retrofit2.http.POST

interface Api {
    @Headers( "Content-Type: application/json")
    @POST("demo/gallery")
    suspend fun createPost(): MainResp

    companion object {
        var retrofitService: Api? = null
        fun getInstance() : Api {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/3/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(Api::class.java)
            }
            return retrofitService!!
        }

    }
}