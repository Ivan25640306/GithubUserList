package com.ivan.android.githubuserlist.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ivan.android.githubuserlist.R
import com.ivan.android.githubuserlist.model.GitHubUser
import com.ivan.android.githubuserlist.util.loadUrlWithCircleCrop
import kotlinx.android.synthetic.main.view_single_user.view.*


class GitHubUsersAdapter(
    val userList: ArrayList<GitHubUser> = ArrayList(),
    val photoSize: Int = -1
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var needCheckSlideBottom: Boolean = false
    var itemSliceBottomHandler: IItemSlideBottom? = null

    companion object {
        const val TYPE_USER = R.layout.view_single_user
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(viewType, parent, false)

        return GitHubUserViewHolder(view)
    }

    fun userDataUpdate(updateUser: List<GitHubUser>) {
        userList.clear()
        userList.addAll(updateUser)
        notifyDataSetChanged()

        needCheckSlideBottom = true
    }

    override fun getItemCount(): Int {
        return userList.size
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is GitHubUserViewHolder && position < userList.size) {
            holder.bindUser(position, userList[position])

            if (position == userList.size - 1 && needCheckSlideBottom) {
                needCheckSlideBottom = false
                itemSliceBottomHandler?.onItemSllideBottom()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return TYPE_USER
    }

    inner class GitHubUserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var pos: Int = 0
        private var user: GitHubUser? = null

        init {
            itemView.setOnClickListener {
                user?.let {
                    val intent = Intent(itemView.context, UserDetailActivity::class.java)
                    intent.putExtra("UserDetail", it.login)
                    itemView.context.startActivity(intent)

                }
            }
        }

        fun bindUser(position: Int, tempUser: GitHubUser) {
            pos = position
            user = tempUser

            updateUi()
        }

        private fun updateUi() {

            user?.let {
                itemView.iv_user_avatar.loadUrlWithCircleCrop(it.avatarUrl, photoSize)
                itemView.tv_user_name.text = it.login
                itemView.tv_badge_site_admin.visibility = if (it.site_admin) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            }
        }
    }

}

interface IItemSlideBottom {
    fun onItemSllideBottom()
}