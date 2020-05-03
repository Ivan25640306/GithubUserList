package com.ivan.android.githubuserlist.ui

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.MergeAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ivan.android.githubuserlist.GitHubAppliction
import com.ivan.android.githubuserlist.R
import com.ivan.android.githubuserlist.util.dpToPx
import com.ivan.android.githubuserlist.viewmodel.UsersViewModel
import kotlinx.android.synthetic.main.activity_users.*
import javax.inject.Inject

class UsersActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: UsersViewModel

    private lateinit var gitHubAdapter: GitHubUsersAdapter
    private val loadingAdapter = LoadingAdapter()
    private lateinit var adapter: MergeAdapter

    private var firstLoading: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as GitHubAppliction).gitHubUserComponet.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)

        viewModel.usersList.observe(this) { value ->
            if (value.isEmpty()) {
                if (firstLoading) {
                    viewModel.checkLoad()
                }
            } else {
                val valueNum = value.size
                gitHubAdapter.userDataUpdate(value)
                viewModel.setSince(value[valueNum - 1].id)
            }
            firstLoading = false
        }

        viewModel.loadState.observe(this) { loadstate ->
            loadingAdapter.loadState = loadstate

            rv_user_list.post {
                loadingAdapter.notifyDataSetChanged()
            }
        }

        gitHubAdapter = GitHubUsersAdapter(photoSize = this.dpToPx(58.0f))
        adapter = MergeAdapter(gitHubAdapter, loadingAdapter)

        rv_user_list.layoutManager = LinearLayoutManager(this)
        rv_user_list.addItemDecoration(SpacesItemDecoration(this.dpToPx(10.0f)))
        rv_user_list.adapter = adapter
        gitHubAdapter.itemSliceBottomHandler = object : IItemSlideBottom {
            override fun onItemSllideBottom() {
                viewModel.checkLoad()
            }
        }


    }

    inner class SpacesItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.left = space
            outRect.right = space
            outRect.bottom = space

            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = space
            }
        }
    }


}

