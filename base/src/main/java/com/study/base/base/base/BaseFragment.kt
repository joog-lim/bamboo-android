package com.study.base.base.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

//import com.example.data.utils.DataStoreManager

open class BaseFragment<T : ViewDataBinding>(@LayoutRes val layoutRes: Int) : Fragment() {


//    @Inject
//    protected lateinit var dataStore: DataStoreManager
    lateinit var binding: T
    protected var token = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        binding.onCreateView()
        binding.onViewCreated()
        getToken()
        return binding.root
    }

    open fun T.onCreateView() = Unit
    open fun T.onViewCreated() = Unit

    private fun getToken() {
//        dataStore.readToken.asLiveData().observe(viewLifecycleOwner) {
//            token = it.token
//        }
    }
}