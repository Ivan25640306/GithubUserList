package com.ivan.android.githubuserlist.util

import androidx.lifecycle.MutableLiveData
import com.ivan.android.githubuserlist.data.RequestError
import com.ivan.android.githubuserlist.ui.LoadState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun CoroutineScope.launchDataLoad(
    loadState: MutableLiveData<LoadState>,
    block: suspend () -> Unit
): Unit {
    launch {
        try {
            loadState.value = LoadState.Loading
            block()
            loadState.value = LoadState.Done
        } catch (error: RequestError) {
            loadState.value = LoadState.Error(error)
        } catch (e: Throwable) {
            loadState.value = LoadState.Error(e)
        }
    }
}