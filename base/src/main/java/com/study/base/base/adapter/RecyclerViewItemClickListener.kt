package com.study.base.base.adapter

interface RecyclerViewItemClickListener<T> {
    fun onclick(data: T): Unit
}

interface RecyclerViewDialogItemClickListener<T> {
    fun oncDialogClick(data: T, state: String): Unit
}