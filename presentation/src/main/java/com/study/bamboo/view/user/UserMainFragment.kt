package com.study.bamboo.view.user

import android.content.res.Resources
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.study.bamboo.R
import com.study.bamboo.adapter.AlgorithmAdapter
import com.study.bamboo.adapter.STATUS
import com.study.bamboo.databinding.FragmentUserMainBinding
import com.study.bamboo.view.admin.AuthViewModel
import com.study.bamboo.view.user.viewmodel.AlgorithmViewModel
import com.study.bamboo.view.user.viewmodel.EmojiViewModel
import com.study.base.base.adapter.PostLoadingAdapter
import com.study.base.base.adapter.RecyclerViewItemClickListener
import com.study.base.base.base.BaseFragment
import com.study.base.base.utils.LinearLayoutManagerWrapper
import com.study.domain.model.common.algorithm.ResultEntity
import com.study.domain.model.user.request.EmojiEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserMainFragment : BaseFragment<FragmentUserMainBinding>(R.layout.fragment_user_main),
    RecyclerViewItemClickListener<ResultEntity> {

    private val mainViewModel by viewModels<AlgorithmViewModel>()
    private val authViewModel by viewModels<AuthViewModel>()
    private val userViewModel by viewModels<EmojiViewModel>()


    private val userAlgorithmAdapter: AlgorithmAdapter by lazy {
        AlgorithmAdapter(
            this,
            STATUS.USER
        )
    }

    override fun FragmentUserMainBinding.onCreateView() {
        binding.fragment = this@UserMainFragment

    }


    override fun FragmentUserMainBinding.onViewCreated() {
        initRecyclerView()
        getAlgorithmList()
        stateAdapter()
        refreshButtonAndLayout()

        with(userViewModel) {
            isSuccess.observe(viewLifecycleOwner) {
                userAlgorithmAdapter.refresh()
            }
            isFailure.observe(viewLifecycleOwner) {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT)
                    .setAction("OK") {

                    }.show()
            }
        }
    }

    fun postBtnClick() {
        findNavController().navigate(R.id.action_userMainFragment_to_postCreateFragment)
    }

    private fun refreshButtonAndLayout() {
        binding.refreshLayout.setOnRefreshListener {

            userAlgorithmAdapter.refresh()
            binding.refreshLayout.isRefreshing = false

        }


    }

    private fun initRecyclerView() {

        val linearLayoutManagerWrapper =
            context?.let { LinearLayoutManagerWrapper(it, LinearLayoutManager.VERTICAL, false) }

        binding.postRecyclerView.apply {
            adapter = userAlgorithmAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            this.adapter = userAlgorithmAdapter.withLoadStateHeaderAndFooter(
                header = PostLoadingAdapter { userAlgorithmAdapter.retry() },
                footer = PostLoadingAdapter { userAlgorithmAdapter.retry() },
            )
            this.layoutManager = linearLayoutManagerWrapper
        }


    }

    fun retrySetButton() {
        userAlgorithmAdapter.retry()
    }

    private fun getAlgorithmList() {
        lifecycleScope.launch {
            mainViewModel.getAlgorithm(authViewModel.getToken()).collectLatest {
                userAlgorithmAdapter.submitData(
                    it
                )
            }
        }
    }

    private fun stateAdapter() {

        userAlgorithmAdapter.addLoadStateListener { combinedLoadStates ->


            when (combinedLoadStates.source.refresh) {
                is LoadState.Error -> {
                    binding.errorRetryBtn.isVisible = true
                    binding.errorText.isVisible = true
                    binding.errorImg.isVisible = true
                    binding.errorNText.isVisible = true

                }
                is LoadState.Loading -> {
                    with(binding) {
                        errorRetryBtn.isVisible = false
                        errorText.isVisible = false
                        errorNText.isVisible = false
                        errorImg.isVisible = false
                        notFoundImg.isVisible = false
                        notFoundText.isVisible = false
                    }

                }
                is LoadState.NotLoading -> {
                    binding.notFoundImg.isVisible =
                        combinedLoadStates.append.endOfPaginationReached and (userAlgorithmAdapter.itemCount < 1)
                    binding.notFoundText.isVisible =
                        combinedLoadStates.append.endOfPaginationReached and (userAlgorithmAdapter.itemCount < 1)

                }

            }


        }

    }


    override fun onStateClick(data: ResultEntity, state: String) {

        val action =
            UserMainFragmentDirections.actionUserMainFragmentToDeclarationFragment(data.idx)
        findNavController().navigate(action)

    }

    override fun onLeafClick(data: ResultEntity) {

        if (data.isClicked) {
            lifecycleScope.launch {
                userViewModel.deleteEmoji(authViewModel.getToken(), EmojiEntity(data.idx))
            }

        } else {
            lifecycleScope.launch {
                userViewModel.postEmoji(authViewModel.getToken(), EmojiEntity(data.idx))
            }
        }

    }


}