package com.ivan.android.githubuserlist.util

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ivan.android.githubuserlist.data.RequestError
import com.ivan.android.githubuserlist.ui.LoadState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.lang.Exception

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