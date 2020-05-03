package com.ivan.android.githubuserlist.di

import android.content.Context
import androidx.room.Room
import com.ivan.android.githubuserlist.data.GitHubUsersRepostory
import com.ivan.android.githubuserlist.db.GitHubDao
import com.ivan.android.githubuserlist.db.GitHubDatabase
import com.ivan.android.githubuserlist.network.GitHubService
import com.ivan.android.githubuserlist.viewmodel.UserDetailViewModelFactory
import com.ivan.android.githubuserlist.viewmodel.UsersViewModelFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class AppModule {

    companion object {
        private const val BASE_URL = "https://api.github.com/"
    }

    @Provides
    @Singleton
    fun provideUsersViewModelFactory(repostory: GitHubUsersRepostory) =
        UsersViewModelFactory(repostory)


    @Provides
    @Singleton
    fun provideDetailViewModelFactory(repostory: GitHubUsersRepostory) =
        UserDetailViewModelFactory(repostory)


    @Provides
    @Singleton
    fun provideGitHubDatabase(context: Context): GitHubDatabase = Room.databaseBuilder(
        context.applicationContext,
        GitHubDatabase::class.java, "Github.db"
    ).build()

    @Provides
    @Singleton
    fun provideGitHubDao(database: GitHubDatabase): GitHubDao = database.gihubDao()


    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BASIC

        return OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()
    }

    @Provides
    @Singleton
    fun privideGitHubService(okHttpClient: OkHttpClient): GitHubService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GitHubService::class.java)
    }
}