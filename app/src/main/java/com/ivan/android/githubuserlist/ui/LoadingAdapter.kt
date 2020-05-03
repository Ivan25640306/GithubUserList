package com.ivan.android.githubuserlist.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ivan.android.githubuserlist.R

class LoadingAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_LOADING = R.layout.view_progress_bar
    }

    var loadState: LoadState = LoadState.Done

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return LoadingViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(TYPE_LOADING, parent, false)
        )
    }


    private fun showLoadingCount(tempState: LoadState): Int {
        return if (tempState != LoadState.Done) 1 else 0
    }

    override fun getItemCount(): Int {
        return showLoadingCount(loadState)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //nothing
    }

    inner class LoadingViewHolder(view: View) : RecyclerView.ViewHolder(view)
}