package com.pws.agu.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.pws.agu.R
import com.pws.agu.model.ModelFollowers
import kotlinx.android.synthetic.main.item_user.view.*

class AdapterListDataFollowers (private val listDataFollower: ArrayList<ModelFollowers>) :
    RecyclerView.Adapter<AdapterListDataFollowers.ListDataHolder>() {


    fun setData(items: ArrayList<ModelFollowers>) {
        listDataFollower.clear()
        listDataFollower.addAll(items)
        notifyDataSetChanged()
    }

    inner class ListDataHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(dataFollowers: ModelFollowers) {
            with(itemView) {
                Glide.with(context)
                    .load(dataFollowers.avatar)
                    .apply(RequestOptions().override(100, 100))
                    .into(user_avatar)

                user_fullname.text = dataFollowers.userName
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListDataHolder {
        return ListDataHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return listDataFollower.size
    }

    override fun onBindViewHolder(holder: ListDataHolder, position: Int) {
        holder.bind(listDataFollower[position])
    }
}