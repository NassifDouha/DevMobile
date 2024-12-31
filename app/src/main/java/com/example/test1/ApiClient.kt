package com.example.test1

import com.example.test1.brut.RetrofitInstance

object ApiClient {
    val apiService: ApiService by lazy {
        RetrofitInstance.retrofit.create(ApiService::class.java)
    }
}