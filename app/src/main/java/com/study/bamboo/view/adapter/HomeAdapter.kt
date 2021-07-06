package com.study.bamboo.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.study.bamboo.R

class HomeAdapter : RecyclerView.Adapter<HomeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem =
            layoutInflater.inflate(R.layout.post_recycler_item, parent, false)

        return HomeViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 15
    }
}

class HomeViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

}