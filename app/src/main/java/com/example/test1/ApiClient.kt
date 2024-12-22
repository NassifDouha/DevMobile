package com.example.test1

object ApiClient {
    val apiService: ApiService by lazy {
        RetrofitInstance.retrofit.create(ApiService::class.java)
    }
}