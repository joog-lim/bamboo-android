package com.study.bamboo.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.study.bamboo.R
import com.study.bamboo.databinding.FragmentHomeBinding
import com.study.bamboo.utils.Functions
import com.study.bamboo.view.adapter.HomeAdapter


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false)
        Functions.recyclerViewManager(binding.postRecyclerView,requireContext())
        binding.postRecyclerView.adapter = HomeAdapter()

        return binding.root
    }

}