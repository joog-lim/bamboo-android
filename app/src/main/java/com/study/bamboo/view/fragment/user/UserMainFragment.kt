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

class UserMainFragment : Fragment() {

    private val signInViewModel: SignInViewModel by viewModels()
    lateinit var binding: FragmentUserMainBinding
    private val mainViewModel by viewModels<MainViewModel>()
    private val postCreateViewModel by viewModels<PostCreateViewModel>()

    companion object {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onStart() {
        super.onStart()
/*        binding.progressBar.visibility = View.GONE
        postCreateViewModel.postCreateSuccess.value = false
        postCreateViewModel.postCreateResponse.value = null*/
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun onResume() {
        super.onResume()
/*        binding.progressBar.visibility = View.GONE
        postCreateViewModel.postCreateSuccess.value = false
        postCreateViewModel.postCreateResponse.value = null*/
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
        //binding.postRecyclerView.adapter = UserHomeItemAdapter(signInViewModel.getPostResponse)
        Log.d("로그", "mainViewModel.getPostResponse : ${mainViewModel.getPostResponse.value}")
        binding.postRecyclerView.adapter = UserHomeItemAdapter(mainViewModel.getPostResponse)

    }

    private fun observeViewModel() {
        mainViewModel.getPostResponse.observe(requireActivity(), Observer {
            Log.d("로그", "UserMainFragment getPostResponse : $it")
            if (it != null) {
                binding.progressBar.visibility = View.GONE
                initRecyclerView()
            }
        })
    }
}