package com.ivan.android.githubuserlist

import android.app.Application
import com.ivan.android.githubuserlist.di.DaggerGitHubUserComponent
import com.ivan.android.githubuserlist.di.GitHubUserComponent

class GitHubAppliction : Application() {

    val gitHubUserComponet: GitHubUserComponent by lazy {
        DaggerGitHubUserComponent.factory().create(applicationContext)
    }

}