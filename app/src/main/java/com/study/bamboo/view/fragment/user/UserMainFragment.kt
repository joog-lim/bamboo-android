package com.study.bamboo.view.fragment.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.study.bamboo.R
import com.study.bamboo.databinding.FragmentUserMainBinding
import com.study.bamboo.utils.Functions
import com.study.bamboo.view.activity.main.MainViewModel
import com.study.bamboo.view.activity.postcreate.PostCreateActivity
import com.study.bamboo.view.activity.postcreate.PostCreateViewModel
import com.study.bamboo.view.activity.signin.SignInViewModel
import com.study.bamboo.view.adapter.UserHomeItemAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserMainFragment : Fragment() {

    lateinit var binding: FragmentUserMainBinding
    private val mainViewModel by viewModels<MainViewModel>()
    private var firstStart = true

    override fun onStart() {
        super.onStart()
        if (firstStart) {
            binding.progressBar.visibility = View.VISIBLE
            firstStart = false
        } else
            binding.progressBar.visibility = View.GONE
    }

    override fun onStop() {
        super.onStop()
        binding.progressBar.visibility = View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_main, container, false)
        binding.activity = this
        binding.progressBar.visibility = View.GONE
        mainViewModel.callGetPost(20, "60b8407473d81a1b4cc591a5", "PENDING")
        observeViewModel()

        return binding.root
    }

    fun addPostBtnClick(view: View) {
        binding.progressBar.visibility = View.VISIBLE
        val intent = Intent(requireContext(), PostCreateActivity::class.java)
        startActivity(intent)
    }

    private fun initRecyclerView() {
        Functions.recyclerViewManager(binding.postRecyclerView, requireContext())
        arguments?.getString("count")
        binding.postRecyclerView.adapter = UserHomeItemAdapter(mainViewModel.getPostResponse)

    }

    private fun observeViewModel() {
        mainViewModel.getPostResponse.observe(requireActivity(), Observer {
            if (it != null) {
                binding.progressBar.visibility = View.GONE
                initRecyclerView()
            }
        })
    }
}