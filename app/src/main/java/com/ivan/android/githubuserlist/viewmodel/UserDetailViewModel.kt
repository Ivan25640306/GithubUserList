package com.ivan.android.githubuserlist.viewmodel

import androidx.lifecycle.*
import com.ivan.android.githubuserlist.data.GitHubUsersRepostory
import com.ivan.android.githubuserlist.model.GitHubUserDetail
import com.ivan.android.githubuserlist.ui.LoadState
import com.ivan.android.githubuserlist.util.launchDataLoad
import javax.inject.Inject

class UserDetailViewModel @Inject constructor(private val repostory: GitHubUsersRepostory) :
    ViewModel() {

    private val _loadState = MutableLiveData<LoadState>(LoadState.Done)

    val loadState: LiveData<LoadState>
        get() = _loadState

    private val _login = MutableLiveData<String>("")

    val userDetail: LiveData<GitHubUserDetail?> = _login.switchMap { repostory.getUserDetail(it) }

    fun setUserName(userName: String) {
        _login.value = userName
        viewModelScope.launchDataLoad(_loadState) { repostory.feachUserDetail(userName) }
    }

    fun reLoad() {
        viewModelScope.launchDataLoad(_loadState) { repostory.feachUserDetail(_login.value ?: "") }
    }

}

class UserDetailViewModelFactory(private val repostory: GitHubUsersRepostory) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserDetailViewModel(repostory) as T
    }

}