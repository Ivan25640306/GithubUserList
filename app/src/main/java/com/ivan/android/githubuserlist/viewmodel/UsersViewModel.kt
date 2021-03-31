package com.ivan.android.githubuserlist.viewmodel

import androidx.lifecycle.*
import com.ivan.android.githubuserlist.data.GitHubUsersRepository
import com.ivan.android.githubuserlist.model.GitHubUser
import com.ivan.android.githubuserlist.ui.LoadState
import com.ivan.android.githubuserlist.util.launchDataLoad
import javax.inject.Inject

class UsersViewModel @Inject constructor(private val repository: GitHubUsersRepository) :
    ViewModel() {

    private val since: MutableLiveData<Int> = MutableLiveData(0)

    val usersList: LiveData<List<GitHubUser>> = repository.users

    private val _loadState = MutableLiveData<LoadState>(LoadState.Done)


    val loadState: LiveData<LoadState>
        get() = _loadState

    fun setSince(tempSince: Int) {
        since.postValue(tempSince)
    }

    fun checkLoad() {
        usersList.value?.let { users ->
            val usersNum = users.size
            if (usersNum < 100) {

                viewModelScope.launchDataLoad(_loadState) { repository.feachUsers(since.value ?: 0) }
            }
        }

    }

}

class UsersViewModelFactory(private val repository: GitHubUsersRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UsersViewModel(repository) as T
    }
}