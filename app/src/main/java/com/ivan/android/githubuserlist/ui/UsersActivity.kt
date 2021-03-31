package com.ivan.android.githubuserlist.ui

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ivan.android.githubuserlist.GitHubAppliction
import com.ivan.android.githubuserlist.R
import com.ivan.android.githubuserlist.util.dpToPx
import com.ivan.android.githubuserlist.viewmodel.UsersViewModel
import kotlinx.android.synthetic.main.activity_users.*
import kotlinx.android.synthetic.main.view_error_page.*
import javax.inject.Inject

class UsersActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: UsersViewModel

    private lateinit var gitHubAdapter: GitHubUsersAdapter
    private val loadingAdapter = LoadingAdapter()
    private lateinit var adapter: ConcatAdapter

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

        viewModel.loadState.observe(this) { loadState ->

            if (loadState is LoadState.Error && gitHubAdapter.itemCount == 0) {
                layout_error.visibility = View.VISIBLE
                rv_user_list.visibility = View.INVISIBLE
                tv_error_msg.text = loadState.error.message

            } else {
                loadingAdapter.loadState = loadState
                rv_user_list.post {
                    loadingAdapter.notifyDataSetChanged()
                }
            }

        }

        btn_reload.setOnClickListener {
            layout_error.visibility = View.INVISIBLE
            rv_user_list.visibility = View.VISIBLE
            viewModel.checkLoad()
        }

        gitHubAdapter = GitHubUsersAdapter(photoSize = this.dpToPx(58.0f))
        adapter = ConcatAdapter(gitHubAdapter, loadingAdapter)

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

