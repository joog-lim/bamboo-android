package com.study.base.base.adapter

interface RecyclerViewItemClickListener<T> {
    fun onclick(data: T): Unit
}