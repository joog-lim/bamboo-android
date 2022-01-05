package com.study.bamboo.view.fragment.user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.study.bamboo.R
import com.study.bamboo.adapter.user.UserHomeItemAdapter
import com.study.bamboo.base.BaseFragment
import com.study.bamboo.data.paging.GetPostSource.Companion.FIRST_PAGE_INDEX
import com.study.bamboo.databinding.FragmentUserMainBinding
import com.study.bamboo.view.activity.main.MainViewModel
import com.study.bamboo.view.activity.signin.SignInActivity
import com.study.bamboo.view.fragment.user.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserMainFragment : BaseFragment<FragmentUserMainBinding>(R.layout.fragment_user_main) {

    //lateinit var binding: FragmentUserMainBinding
    private val mainViewModel by viewModels<MainViewModel>()
    private val userViewModel by viewModels<UserViewModel>()

    private var firstStart = true

    private lateinit var callback: OnBackPressedCallback
    override fun onStop() {
        super.onStop()
        binding.progressBar.visibility = View.GONE
    }

    override fun onStart() {
        super.onStart()
        Log.d("로그", "시작됨 start")
        firstStart = true
        //initRecyclerView()
        // mainViewModel.setGetPostResponse(null)
    }

    override fun onResume() {
        super.onResume()
        Log.d("로그", "시작됨 resume")
        mainViewModel.callGetPost(20, "ACCEPTED")
        if (firstStart) {
            binding.progressBar.visibility = View.VISIBLE
            firstStart = false
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_main, container, false)
        binding.activity = this
        Log.d("로그", "시작됨 createview")
        binding.progressBar.visibility = View.GONE
        observeViewModel()


        return binding.root
    }

    fun addPostBtnClick(view: View) {
        view.findNavController().navigate(R.id.action_userMainFragment_to_postCreateFragment)
    }


    private fun initRecyclerView() {
        //Functions.recyclerViewManager(binding.postRecyclerView, requireContext())
        binding.postRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val userHomeItemAdapter =
            UserHomeItemAdapter(userViewModel)
        binding.postRecyclerView.adapter =
            userHomeItemAdapter

        lifecycleScope.launch {
            mainViewModel.pagingData.collectLatest {
                (userHomeItemAdapter).submitData(
                    it
                )
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Log.d("TAG", "handleOnBackPressed: ")
                startActivity(Intent(requireContext(), SignInActivity::class.java))

            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }


    private fun observeViewModel() {
        Log.d(
            "로그",
            "post : ${mainViewModel.getPostResponse.value}, count : ${mainViewModel.getCountResponse.value}"
        )
        mainViewModel.getPostResponse.observe(requireActivity(), Observer {
            Log.d("로그", "in post : ${it?.size}")
            if (it != null) {
                binding.progressBar.visibility = View.GONE
                FIRST_PAGE_INDEX = 1
                initRecyclerView()
            }
        })

/*        mainViewModel.getVerifyResponse.observe(requireActivity(), Observer {
            if (it != null) {
                binding.progressBar.visibility = View.GONE
                getVerifyResponse = it
                val intent = Intent(requireContext(), PostCreateActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(requireContext(), "서버와 연결에 실패했습니다",Toast.LENGTH_SHORT).show()
                binding.progressBar.visibility = View.GONE
            }
        })*/
    }
}