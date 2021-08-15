package com.study.bamboo.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.study.bamboo.R
import com.study.bamboo.databinding.FragmentUserMainBinding
import com.study.bamboo.utils.Functions
import com.study.bamboo.utils.ViewModel.signInViewModel
import com.study.bamboo.view.adapter.UserHomeItemAdapter

class UserMainFragment : Fragment() {

    lateinit var binding: FragmentUserMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_user_main,container,false)

        Functions.recyclerViewManager(binding.postRecyclerView,requireContext())
        binding.postRecyclerView.adapter = UserHomeItemAdapter(signInViewModel.getPostResponse)



        return binding.root
    }

}