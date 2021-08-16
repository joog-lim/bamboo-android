package com.study.bamboo.view.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.study.bamboo.R
import com.study.bamboo.databinding.FragmentUserMainBinding
import com.study.bamboo.utils.Functions
import com.study.bamboo.utils.ViewModel.postCreateViewModel
import com.study.bamboo.utils.ViewModel.signInViewModel
import com.study.bamboo.view.PostCreateActivity
import com.study.bamboo.view.adapter.UserHomeItemAdapter
import com.study.bamboo.viewmodel.PostCreateViewModel
import com.study.bamboo.viewmodel.SignInViewModel

class UserMainFragment : Fragment() {

    lateinit var binding: FragmentUserMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onStart() {
        super.onStart()
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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_user_main,container,false)
        binding.activity = this
        binding.progressBar.visibility = View.GONE

        Functions.recyclerViewManager(binding.postRecyclerView,requireContext())
        binding.postRecyclerView.adapter = UserHomeItemAdapter(signInViewModel.getPostResponse)

        postCreateViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(PostCreateViewModel::class.java)


        //글쓰기 버튼을 눌렀을때 verify 호출 후 완료 했을때
        postCreateViewModel.getVerifyResponse.observe(requireActivity(), Observer {
            val intent = Intent(requireContext(), PostCreateActivity::class.java)
            startActivity(intent)
        })


        return binding.root
    }

    fun addPostBtnClick(view: View){
        binding.progressBar.visibility = View.VISIBLE
        postCreateViewModel.callGetVerifyAPI()

    }

}