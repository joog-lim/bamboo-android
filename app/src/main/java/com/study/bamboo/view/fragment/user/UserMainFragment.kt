package com.study.bamboo.view.fragment.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment

import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.study.bamboo.R
import com.study.bamboo.adapter.user.UserHomeItemAdapter
import com.study.bamboo.base.BaseFragment
import com.study.bamboo.data.network.models.user.GetVerifyDTO
import com.study.bamboo.data.network.models.user.getcount.GetCount
import com.study.bamboo.databinding.FragmentUserMainBinding
import com.study.bamboo.utils.Functions
import com.study.bamboo.view.activity.main.MainViewModel
import com.study.bamboo.data.paging.GetPostSource
import com.study.bamboo.view.activity.postcreate.PostCreateViewModel
import com.study.bamboo.view.activity.signin.SignInActivity.Companion.getPostCountResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserMainFragment : BaseFragment<FragmentUserMainBinding>(R.layout.fragment_user_main) {

    //lateinit var binding: FragmentUserMainBinding
    private val mainViewModel by activityViewModels<MainViewModel>()
    lateinit var userHomeItemAdapter: UserHomeItemAdapter
    private val postCreateViewModel : PostCreateViewModel by activityViewModels()

    companion object {
        private var firstStart = true
        var getVerifyResponse: GetVerifyDTO? = null

    }

    override fun onStop() {
        super.onStop()
        binding.progressBar.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
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
        binding.progressBar.visibility = View.GONE
        observeViewModel()


        return binding.root
    }

    fun addPostBtnClick(view: View) {
/*        binding.progressBar.visibility = View.VISIBLE
        if (getVerifyResponse != null){
            binding.progressBar.visibility = View.GONE
            val intent = Intent(requireContext(), PostCreateActivity::class.java)
            startActivity(intent)
        }else{
            mainViewModel.callGetVerify()
        }*/
       //postCreateViewModel.setPostCreateSuccess(false)
        view.findNavController().navigate(R.id.action_userMainFragment_to_postCreateFragment)
    }


    private fun initRecyclerView() {
        Functions.recyclerViewManager(binding.postRecyclerView, requireContext())
        arguments?.getString("count")
        binding.postRecyclerView.adapter =
            UserHomeItemAdapter()
        userHomeItemAdapter =
            UserHomeItemAdapter()

/*        lifecycleScope.launchWhenCreated {
            mainViewModel.getListData(getPostCountResponse).collect {
                userHomeItemAdapter.submitData(it)
            }
        }*/

        lifecycleScope.launch {
            mainViewModel.pagingData.collectLatest {
                (binding.postRecyclerView.adapter as UserHomeItemAdapter).submitData(
                    it
                )
            }
        }
    }


    private fun observeViewModel() {
        Log.d(
            "로그",
            "post : ${mainViewModel.getPostResponse.value}, count : ${mainViewModel.getCountResponse.value}"
        )
        mainViewModel.getPostResponse.observe(requireActivity(), Observer {
            Log.d("로그", "in post : $it")
            if (it != null) {
                binding.progressBar.visibility = View.GONE
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