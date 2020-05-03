package com.ivan.android.githubuserlist.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ivan.android.githubuserlist.model.GitHubUser
import com.ivan.android.githubuserlist.model.GitHubUserDetail

@Database(
    entities = [GitHubUser::class, GitHubUserDetail::class],
    version = 1,
    exportSchema = false
)
abstract class GitHubDatabase : RoomDatabase() {
    abstract fun gihubDao(): GitHubDao
}