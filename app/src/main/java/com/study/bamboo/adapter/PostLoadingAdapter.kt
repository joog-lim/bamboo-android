package com.study.bamboo.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.study.bamboo.R
import com.study.bamboo.databinding.PostLoadingBinding

class PostLoadingAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<PostLoadingAdapter.LoadStateHolder>() {
    inner class LoadStateHolder(val binding: PostLoadingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryBtn.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
                Log.d("PostLoadingAdapter", "bind: $loadState")
                progressBar.isVisible = loadState is LoadState.Loading
                retryBtn.isVisible = loadState !is LoadState.Loading
            }
        }


    }

    override fun onBindViewHolder(holder: LoadStateHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: PostLoadingBinding = DataBindingUtil
            .inflate(
                layoutInflater, R.layout.post_loading,
                parent, false
            )
        return LoadStateHolder(binding)
    }

}