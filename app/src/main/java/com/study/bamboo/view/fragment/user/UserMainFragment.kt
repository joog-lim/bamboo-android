package com.study.bamboo.view.fragment.user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.study.bamboo.R
import com.study.bamboo.databinding.FragmentUserMainBinding
import com.study.bamboo.utils.Functions
import com.study.bamboo.utils.Variable
import com.study.bamboo.view.activity.main.MainViewModel
import com.study.bamboo.view.activity.postcreate.PostCreateActivity
import com.study.bamboo.view.activity.postcreate.PostCreateViewModel
import com.study.bamboo.view.activity.signin.SignInViewModel
import com.study.bamboo.view.adapter.UserHomeItemAdapter

class UserMainFragment : Fragment() {

    lateinit var binding: FragmentUserMainBinding
    lateinit var fragmentContext : Context
    private val mainViewModel: MainViewModel by activityViewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                MainViewModel() as T
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("로그","start")
        if (mainViewModel.getPostResponse.value == null){
            binding.progressBar.visibility = View.VISIBLE
        }
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
        observeViewModel(fragmentContext)

        return binding.root
    }

    fun addPostBtnClick(view: View) {
        binding.progressBar.visibility = View.VISIBLE
        val intent = Intent(requireContext(), PostCreateActivity::class.java)
        startActivity(intent)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentContext = context
    }

    private fun initRecyclerView(context : Context) {
        Functions.recyclerViewManager(binding.postRecyclerView, context)
        binding.postRecyclerView.adapter = UserHomeItemAdapter(mainViewModel.getPostResponse)
    }

    private fun observeViewModel(context : Context){
        mainViewModel.getPostResponse.observe(requireActivity(), Observer {
            Log.d("로그","UserMainFragment getPostResponse : $it")
            if (it != null){
                binding.progressBar.visibility = View.GONE
                initRecyclerView(context)
            }
        })
    }
}