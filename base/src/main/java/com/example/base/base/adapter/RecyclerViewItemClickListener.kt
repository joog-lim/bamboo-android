package com.example.base.base.adapter

interface RecyclerViewItemClickListener<T> {
    fun onclick(data: T): Unit
}