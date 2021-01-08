package com.pws.agu.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.pws.agu.R
import com.pws.agu.model.ModelUsers
import com.pws.agu.view.activity.DetailActivity
import kotlinx.android.synthetic.main.item_user.view.*


class AdapterListDataUsers(private val listDataUsersGithub: ArrayList<ModelUsers>) :
    RecyclerView.Adapter<AdapterListDataUsers.ListDataHolder>() {

    fun setData(items: ArrayList<ModelUsers>) {
        listDataUsersGithub.clear()
        listDataUsersGithub.addAll(items)
        notifyDataSetChanged()
    }

    inner class ListDataHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(modelUsers: ModelUsers) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(modelUsers.avatar)
                    .apply(RequestOptions().override(100, 100))
                    .into(user_avatar)

                user_fullname.text = modelUsers.userName

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListDataHolder {
        return ListDataHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return listDataUsersGithub.size
    }

    override fun onBindViewHolder(holder: ListDataHolder, position: Int) {
        holder.bind(listDataUsersGithub[position])

        val data = listDataUsersGithub[position]
        holder.itemView.setOnClickListener {
            val dataUserIntent = ModelUsers(
                data.userName,
                data.name,
                data.avatar,
                data.company,
                data.location,
                data.repository,
                data.followers,
                data.following
            )
            val mIntent = Intent(it.context, DetailActivity::class.java)
            mIntent.putExtra(DetailActivity.EXTRA_DETAIL, dataUserIntent)
            it.context.startActivity(mIntent)
        }
    }


}