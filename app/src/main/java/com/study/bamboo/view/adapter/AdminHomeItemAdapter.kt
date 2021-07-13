package com.study.bamboo.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.study.bamboo.R

class AdminHomeItemAdapter : RecyclerView.Adapter<AdminHomeItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminHomeItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem =
            layoutInflater.inflate(R.layout.admin_post_recycler_item, parent, false)

        return AdminHomeItemViewHolder(listItem)
    }

    override fun onBindViewHolder(holderUser: AdminHomeItemViewHolder, position: Int) {


    }

    override fun getItemCount(): Int {
        return 15
    }
}

class AdminHomeItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

}