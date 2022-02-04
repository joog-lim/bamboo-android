package com.study.base.base.adapter


interface RecyclerViewItemClickListener<T> {
    fun onStateClick(data: T, state: String): Unit
    fun onLeafClick(data: T): Unit

}