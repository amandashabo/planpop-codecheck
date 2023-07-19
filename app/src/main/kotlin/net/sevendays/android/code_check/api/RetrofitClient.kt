package net.sevendays.android.code_check.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    val instance: GitHubApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(GitHubApiService::class.java)
    }

}