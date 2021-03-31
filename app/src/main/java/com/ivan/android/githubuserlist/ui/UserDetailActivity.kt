package com.ivan.android.githubuserlist.ui

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.ivan.android.githubuserlist.R
import com.ivan.android.githubuserlist.viewmodel.UserDetailViewModel
import androidx.lifecycle.observe
import com.ivan.android.githubuserlist.GitHubAppliction
import com.ivan.android.githubuserlist.model.GitHubUserDetail
import com.ivan.android.githubuserlist.util.loadUrlWithCircleCrop
import kotlinx.android.synthetic.main.activity_user_detail.*;
import kotlinx.android.synthetic.main.view_error_page.*
import javax.inject.Inject

class UserDetailActivity : AppCompatActivity() {

    companion object {
        const val ATTR_USER_DETAIL = "UserDetail"
    }

    @Inject
    lateinit var viewModel: UserDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as GitHubAppliction).gitHubUserComponet.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        val userName = intent.getStringExtra(ATTR_USER_DETAIL)

        viewModel.loadState.observe(this,
            Observer { loadState ->
                when (loadState) {
                    LoadState.Done -> {
                        layout_content.visibility = View.VISIBLE
                        layout_error.visibility = View.INVISIBLE
                        spinner.visibility = View.INVISIBLE
                    }

                    LoadState.Loading -> {
                        layout_content.visibility = View.INVISIBLE
                        layout_error.visibility = View.INVISIBLE
                        spinner.visibility = View.VISIBLE
                    }

                    is LoadState.Error -> {
                        layout_content.visibility = View.INVISIBLE
                        layout_error.visibility = View.VISIBLE
                        spinner.visibility = View.INVISIBLE
                        tv_error_msg.text = loadState.error.message
                    }
                }

            })

        viewModel.userDetail.observe(this,
            Observer { detail ->
                detail?.let {
                    updateUI(it)
                }
            })

        btn_reload.setOnClickListener {
            viewModel.reLoad()
        }

        iv_close.setOnClickListener {
            finish()
        }

        userName?.let {
            viewModel.setUserName(it)
        } ?: run {
            finish()
        }

    }

    private fun updateUI(detail: GitHubUserDetail) {
        iv_user_avatar.loadUrlWithCircleCrop(detail.avatarUrl)
        tv_user_name.text = detail.name
        tv_bio.text = detail.bio ?: ""
        tv_login_name.text = detail.login
        tv_badge_site_admin.visibility = if (detail.site_admin) View.VISIBLE else View.GONE
        tv_location.text = detail.location
        tv_link.text = detail.blog
        tv_link.movementMethod = LinkMovementMethod.getInstance()
    }
}