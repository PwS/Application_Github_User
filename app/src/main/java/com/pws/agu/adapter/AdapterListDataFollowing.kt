package com.pws.agu.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.pws.agu.R
import com.pws.agu.model.ModelFollowing
import kotlinx.android.synthetic.main.item_user.view.*

class AdapterListDataFollowing (private val listDataFollowing: ArrayList<ModelFollowing>) :
    RecyclerView.Adapter<AdapterListDataFollowing.ListDataHolder>() {

    fun setData(items: ArrayList<ModelFollowing>) {
        listDataFollowing.clear()
        listDataFollowing.addAll(items)
        notifyDataSetChanged()
    }

    inner class ListDataHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(dataFollowing: ModelFollowing) {
            with(itemView) {
                Glide.with(context)
                    .load(dataFollowing.avatar)
                    .apply(RequestOptions().override(100, 100))
                    .into(user_avatar)

                user_fullname.text = dataFollowing.userName
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListDataHolder {
        return ListDataHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        )
    }
    override fun getItemCount(): Int {
        return listDataFollowing.size
    }

    override fun onBindViewHolder(holder: ListDataHolder, position: Int) {
        holder.bind(listDataFollowing[position])
    }

}