package com.ivan.android.githubuserlist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.ivan.android.githubuserlist.db.GitHubDao
import com.ivan.android.githubuserlist.model.GitHubUser
import com.ivan.android.githubuserlist.model.GitHubUserDetail
import com.ivan.android.githubuserlist.network.GitHubService
import javax.inject.Inject

class GitHubUsersRepostory @Inject constructor(
    val service: GitHubService,
    val dao: GitHubDao
) {

    val users: LiveData<List<GitHubUser>> = dao.queryUsers()

    suspend fun feachUsers(since: Int) {
        val users = service.fetchUsers(since)
        dao.insertUsers(users)
    }

    fun getUserDetail(login: String): LiveData<GitHubUserDetail?> = dao.queryUserDetail(login)

    suspend fun feachUserDetail(userName: String) {
        val userDetail = service.getUserDetail(userName)
        dao.insertUserDetail(userDetail)
    }


}