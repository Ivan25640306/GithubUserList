package com.ivan.android.githubuserlist.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ivan.android.githubuserlist.model.GitHubUser
import com.ivan.android.githubuserlist.model.GitHubUserDetail

@Dao
interface GitHubDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(userList: List<GitHubUser>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserDetails(userDetailList: List<GitHubUserDetail>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserDetail(userDetail: GitHubUserDetail)

    @Query("SELECT * FROM git_hub_user ORDER BY id ASC LIMIT 100")
    fun queryUsers(): LiveData<List<GitHubUser>>

    @Query("SELECT * FROM git_hub_user_detail WHERE login = :login LIMIT 1")
    fun queryUserDetail(login: String): LiveData<GitHubUserDetail?>
}