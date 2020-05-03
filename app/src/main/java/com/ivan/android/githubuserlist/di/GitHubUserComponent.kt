package com.ivan.android.githubuserlist.di

import android.content.Context
import com.ivan.android.githubuserlist.ui.UsersActivity
import com.ivan.android.githubuserlist.ui.UserDetailActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class])
interface GitHubUserComponent {

    // Factory to create instances of the AppComponent
    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Context): GitHubUserComponent
    }

    fun inject(activity: UsersActivity)
    fun inject(activity: UserDetailActivity)
}