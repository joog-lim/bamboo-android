package com.example.presentation.view

import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.IdRes
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.base.base.adapter.PostLoadingAdapter
import com.example.base.base.adapter.RecyclerViewItemClickListener
import com.example.base.base.base.BaseFragment
import com.example.base.base.utils.LinearLayoutManagerWrapper
import com.example.domain.model.admin.response.PostEntity
import com.example.presentation.adapter.AdminAdapter
import com.example.presentation.adapter.AdminAdapter.Companion.ACCEPTED
import com.example.presentation.adapter.AdminAdapter.Companion.ACCEPTEDType
import com.example.presentation.adapter.AdminAdapter.Companion.DELETED
import com.example.presentation.adapter.AdminAdapter.Companion.DELETEDType
import com.example.presentation.adapter.AdminAdapter.Companion.PENDING
import com.example.presentation.adapter.AdminAdapter.Companion.PENDINGType
import com.example.presentation.adapter.AdminAdapter.Companion.REJECTED
import com.example.presentation.adapter.AdminAdapter.Companion.REJECTEDType
import com.example.presentation.databinding.FragmentAdminMainBinding
import com.example.presentation.utils.DiaryUtil.Companion.DIALOG_RESULT_KEY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.example.presentation.R

// TODO: 2021-08-26


@AndroidEntryPoint
class AdminMainFragment : BaseFragment<FragmentAdminMainBinding>(R.layout.fragment_admin_main),
    RecyclerViewItemClickListener<PostEntity> {

    companion object {
        const val TAG = "AdminMainFragment"
    }

    private val viewModel: AdminViewModel by viewModels()


    private val adminAdapter: AdminAdapter by lazy { AdminAdapter(this) }



    @ExperimentalPagingApi
    override fun FragmentAdminMainBinding.onCreateView() {
        setItemAdapter(ACCEPTEDType)


        binding.activitySpinner.adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.AdminItemList,
            R.layout.admin_spinner_item
        )


        spinnerContact()


    }


    override fun FragmentAdminMainBinding.onViewCreated() {
        val id = findNavController().currentDestination?.id

        id?.let {
            getDialogNavResult<String>(navId = it) { result ->
                when (result) {
                    ACCEPTED -> adminAdapter.refresh()

                }
            }
        }
        onClickUser()


    }





    private fun refreshButtonAndLayout(adapter: PagingDataAdapter<*, *>) {
        binding.refreshLayout.setOnRefreshListener {

            when (adapter) {
                this.adminAdapter -> {
                    this.adminAdapter.refresh()
                    binding.refreshLayout.isRefreshing = false
                }

            }
        }


    }


    @ExperimentalPagingApi
    private fun spinnerContact() {

        binding.activitySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                    when (position) {
                        0 -> {

                            setItemAdapter(ACCEPTEDType)
                            Log.d(TAG, "onItemSelected: $position token :$token")
                            refreshButtonAndLayout(adminAdapter)

                            lifecycleScope.launch {
                                observeNetwork(
                                    ACCEPTEDType
                                )
                            }

                        }
                        1 -> {
                            setItemAdapter(PENDINGType)
                            Log.d(TAG, "onItemSelected: $position token :$token")
                            lifecycleScope.launch {
                                observeNetwork(
                                    PENDINGType

                                )
                            }

                        }
                        2 -> {
                            setItemAdapter(REJECTEDType)
                            Log.d(TAG, "onItemSelected: $position token :$token")
                            lifecycleScope.launch {
                                observeNetwork(
                                    REJECTEDType

                                )

                            }

                        }
                        3 -> {
                            setItemAdapter(DELETEDType)
                            Log.d(TAG, "onItemSelected: $position token :$token")
                            lifecycleScope.launch {
                                observeNetwork(
                                    DELETEDType

                                )

                            }

                        }
                        else -> {
                            setItemAdapter(ACCEPTEDType)
                            Log.d(TAG, "onItemSelected: $position token :$token")
                            refreshButtonAndLayout(adminAdapter)
                            lifecycleScope.launch {
                                observeNetwork(
                                    ACCEPTEDType

                                )
                            }
                        }
                    }
                }
            }

    }




    private fun onClickUser() {
        binding.userText.setOnClickListener {

        }
    }

    // paging 데이터값 받아옴
    @ExperimentalPagingApi
    private fun observePagingData(viewType: Int) {


        if (view != null) {
            when (viewType) {
                ACCEPTEDType -> {
                    lifecycleScope.launch {
                        viewModel.getAlgorithm(token, ACCEPTED).collectLatest {
                            adminAdapter.submitData(it)
                        }


                    }

                }
                DELETEDType -> {
                    lifecycleScope.launch {
                        viewModel.getAlgorithm(token, DELETED).collectLatest {
                            adminAdapter.submitData(it)
                        }

                    }

                }
                PENDINGType -> {

                    lifecycleScope.launch {
                        viewModel.getAlgorithm(token, PENDING).collectLatest {
                            adminAdapter.submitData(it)
                        }


                    }

                }
                REJECTEDType -> {

                    lifecycleScope.launch {

                        viewModel.getAlgorithm(token, REJECTED).collectLatest {
                            adminAdapter.submitData(it)
                        }

                    }


                }

            }

        }
    }


    @ExperimentalPagingApi
    fun observeNetwork(viewType: Int) {

        when (viewType) {
            ACCEPTEDType -> {
                observePagingData(ACCEPTEDType)

            }
            DELETEDType -> {
                observePagingData(DELETEDType)

            }
            PENDINGType -> {
                observePagingData(PENDINGType)

            }
            REJECTEDType -> {
                observePagingData(REJECTEDType)

            }

        }


    }

    private fun stateAdapter(adapter: PagingDataAdapter<*, *>) {

        when (adapter) {
            this.adminAdapter -> {
                this.adminAdapter.addLoadStateListener { combinedLoadStates ->


                    binding.errorRetryBtn.isVisible =
                        combinedLoadStates.source.refresh is LoadState.Error
                    binding.errorText.isVisible =
                        combinedLoadStates.source.refresh is LoadState.Error
                    binding.errorImg.isVisible =
                        combinedLoadStates.source.refresh is LoadState.Error
                    binding.postRecyclerView.isVisible =
                        combinedLoadStates.source.refresh is LoadState.Error

                    binding.errorNText.isVisible =
                        combinedLoadStates.source.refresh is LoadState.Error
                    retrySetButton(this.adminAdapter)


                    when (combinedLoadStates.source.refresh) {
                        is LoadState.NotLoading -> Log.d(TAG, "stateAdapter: 로딩중이아님")
                        is LoadState.Loading -> Log.d(TAG, "stateAdapter: 로딩중")
                        is LoadState.Error -> Log.d(TAG, "stateAdapter: 에러")
                    }

                    if (combinedLoadStates.source.refresh is LoadState.NotLoading
                        && combinedLoadStates.append.endOfPaginationReached
                        && this.adminAdapter.itemCount < 1
                    ) {
                        binding.postRecyclerView.isVisible = false
                        binding.notFoundImg.isVisible = true
                        binding.notFoundText.isVisible = true

                    } else {
                        binding.postRecyclerView.isVisible = true
                        binding.notFoundImg.isVisible = false
                        binding.notFoundText.isVisible = false
                    }

                }
            }

        }
    }

    private fun retrySetButton(adapter: PagingDataAdapter<*, *>) {
        binding.errorRetryBtn.setOnClickListener {
            adapter.retry()
        }
    }

    private fun setItemAdapter(viewType: Int) {


        val linearLayoutManagerWrapepr =
            context?.let { LinearLayoutManagerWrapper(it, LinearLayoutManager.VERTICAL, false) }



        when (viewType) {
            ACCEPTEDType -> {
                binding.postRecyclerView.apply {
                    this.adapter = adminAdapter
                    this.adapter = adminAdapter.withLoadStateHeaderAndFooter(
                        header = PostLoadingAdapter { adminAdapter.retry() },
                        footer = PostLoadingAdapter { adminAdapter.retry() },
                    )
                    this.layoutManager = linearLayoutManagerWrapepr
                }
                stateAdapter(adminAdapter)
            }

        }
    }


    private inline fun <T> getDialogNavResult(
        @IdRes navId: Int,
        key: String = DIALOG_RESULT_KEY,
        crossinline onChanged: (T?) -> Unit
    ) {
        val backStackEntry = findNavController().getBackStackEntry(navId)
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME && backStackEntry.savedStateHandle.contains(key)) {
                val result = backStackEntry.savedStateHandle.get<T>(key)
                onChanged(result)
                backStackEntry.savedStateHandle.remove<T>(key)
            }
        }
        backStackEntry.lifecycle.addObserver(observer)

        viewLifecycleOwner.lifecycle.addObserver(
            LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_DESTROY) {
                    backStackEntry.lifecycle.removeObserver(observer)
                }
            }
        )
    }

    override fun onclick(data: PostEntity) {

    }

}
