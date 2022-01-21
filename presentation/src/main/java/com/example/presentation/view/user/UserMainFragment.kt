package com.example.presentation.view.user

import android.content.Context
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.base.base.base.BaseFragment
import com.example.presentation.R
import com.example.presentation.adapter.UserHomeItemAdapter
import com.example.presentation.databinding.FragmentUserMainBinding
import com.example.presentation.view.user.viewmodel.AlgorithmViewModel
import com.example.presentation.view.user.viewmodel.EmojiViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserMainFragment : BaseFragment<FragmentUserMainBinding>(R.layout.fragment_user_main) {

    private val mainViewModel by viewModels<AlgorithmViewModel>()
    private val userViewModel by viewModels<EmojiViewModel>()

    private lateinit var callback: OnBackPressedCallback

    private val userAlgorithmAdapter: UserHomeItemAdapter by lazy { UserHomeItemAdapter() }
    override fun FragmentUserMainBinding.onCreateView() {
        binding.activity = this@UserMainFragment
    }



    fun addPostBtnClick(view: View) {
        view.findNavController().navigate(R.id.action_userMainFragment_to_postCreateFragment)
    }


    private fun initRecyclerView() {

        binding.postRecyclerView.apply {
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = userAlgorithmAdapter
        }


    }

    private fun getAlgorithmList() {
        lifecycleScope.launch {
            mainViewModel.getAlgorithm(token, "ACCEPTED").collectLatest {
                (userAlgorithmAdapter).submitData(
                    it
                )
            }
        }
    }


    override fun FragmentUserMainBinding.onViewCreated() {
        initRecyclerView()
        getAlgorithmList()

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }


}