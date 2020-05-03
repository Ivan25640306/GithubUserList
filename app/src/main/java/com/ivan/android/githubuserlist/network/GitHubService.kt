package com.ivan.android.githubuserlist.network

import androidx.lifecycle.LiveData
import com.ivan.android.githubuserlist.model.GitHubUser
import com.ivan.android.githubuserlist.model.GitHubUserDetail
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface GitHubService {
    @GET("users")
    suspend fun fetchUsers(
        @Query("since") since: Int,
        @Query("per_page") pageSize: Int = 20
    ): List<GitHubUser>

    @GET("users/{user_name}")
    suspend fun getUserDetail(@Path("user_name") userName: String): GitHubUserDetail
}