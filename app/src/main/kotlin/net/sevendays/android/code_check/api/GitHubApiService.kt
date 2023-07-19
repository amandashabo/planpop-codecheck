package net.sevendays.android.code_check.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GitHubApiService {

    @GET("search/repositories")
    fun searchRepositories(
        @Header("Accept") acceptHeader: String,
        @Query("q") query: String
    ): Call<RepositorySearchResponse>

}