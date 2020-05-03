package com.ivan.android.githubuserlist.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "git_hub_user")
data class GitHubUser(
    @PrimaryKey val id: Int,
    @field:SerializedName("avatar_url") val avatarUrl: String,
    val login: String,
    @field:SerializedName("site_admin") val site_admin: Boolean
)

@Entity(tableName = "git_hub_user_detail")
data class GitHubUserDetail(
    @PrimaryKey val id: Int,
    @field:SerializedName("avatar_url") val avatarUrl: String?,
    val name: String?,
    val bio: String?,
    val login: String,
    @field:SerializedName("site_admin") val site_admin: Boolean,
    val location: String?,
    val blog: String?
)