package com.study.base.base.base

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<T : ViewDataBinding>(@LayoutRes private val contentLayoutId: Int) :
    AppCompatActivity() {


    lateinit var binding: T


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, contentLayoutId)


    }
    protected fun makeToast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()


}