package com.study.bamboo.view.fragment.admin

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.study.bamboo.R
import com.study.bamboo.adapter.PostLoadingAdapter
import com.study.bamboo.adapter.admin.AdminAcceptAdapter
import com.study.bamboo.adapter.admin.AdminAcceptAdapter.Companion.ACCEPTEDType
import com.study.bamboo.adapter.admin.AdminAcceptAdapter.Companion.DELETEDType
import com.study.bamboo.adapter.admin.AdminAcceptAdapter.Companion.PENDINGType
import com.study.bamboo.adapter.admin.AdminAcceptAdapter.Companion.REJECTEDType
import com.study.bamboo.adapter.admin.AdminDeleteAdapter
import com.study.bamboo.adapter.admin.AdminPendingAdapter
import com.study.bamboo.adapter.admin.AdminRejectAdapter
import com.study.bamboo.base.BaseFragment
import com.study.bamboo.data.paging.viewModel.PagingPostViewModel
import com.study.bamboo.databinding.FragmentAdminMainBinding
import com.study.bamboo.utils.LinearLayoutManagerWrapper
import com.study.bamboo.view.fragment.admin.dialog.AcceptDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

// TODO: 2021-08-26


@AndroidEntryPoint
class AdminMainFragment : BaseFragment<FragmentAdminMainBinding>(R.layout.fragment_admin_main) {

    companion object {
        const val TAG = "AdminMainFragment"


    }


    private val viewModel: AdminViewModel by viewModels()
    private lateinit var pagingViewModel: PagingPostViewModel
    private lateinit var acceptDialog: AcceptDialog


    private val acceptAdapter: AdminAcceptAdapter by lazy {
        AdminAcceptAdapter()
    }

    private val deleteAdapter: AdminDeleteAdapter by lazy {

        AdminDeleteAdapter()
    }

    private val rejectAdapter: AdminRejectAdapter by lazy {
        AdminRejectAdapter()

    }

    private val pendingAdapter: AdminPendingAdapter by lazy {


        AdminPendingAdapter()
    }

    var status: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pagingViewModel = ViewModelProvider(requireActivity()).get(PagingPostViewModel::class.java)

    }

    var token = ""
    var cursor = ""

    @ExperimentalPagingApi
    override fun FragmentAdminMainBinding.onCreateView() {
        setItemAdapter(ACCEPTEDType)

        acceptDialog = AcceptDialog()

        binding.activitySpinner.adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.AdminItemList,
            R.layout.admin_spinner_item
        )








        observeUiPreferences()
        spinnerContact()


    }


    override fun FragmentAdminMainBinding.onViewCreated() {
    }


    private fun observeUiPreferences() {
        viewModel.readToken.asLiveData().observe(viewLifecycleOwner, {
            token = it.token
            Log.d(TAG, "observeUiPreferences: ${it.token}")

        })

    }


    private fun refreshButtonAndLayout(adapter: PagingDataAdapter<*, *>) {
        binding.refreshLayout.setOnRefreshListener {

            when (adapter) {
                acceptAdapter -> {
                    acceptAdapter.refresh()
                    binding.refreshLayout.isRefreshing = false
                }
                rejectAdapter -> {
                    rejectAdapter.refresh()
                    binding.refreshLayout.isRefreshing = false
                }
                pendingAdapter -> {
                    pendingAdapter.refresh()
                    binding.refreshLayout.isRefreshing = false
                }
                deleteAdapter -> {
                    pendingAdapter.refresh()
                    binding.refreshLayout.isRefreshing = false
                }
            }
        }
        binding.updateBtn.setOnClickListener {
            when (adapter) {
                acceptAdapter -> acceptAdapter.refresh()
                rejectAdapter -> rejectAdapter.refresh()
                pendingAdapter -> pendingAdapter.refresh()
                deleteAdapter -> pendingAdapter.refresh()
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
                            refreshButtonAndLayout(acceptAdapter)
                            lifecycleScope.launch {
                                observeNetwork(
                                    token,
                                    cursor,
                                    acceptAdapter
                                )
                            }

                        }
                        1 -> {
                            setItemAdapter(PENDINGType)
                            Log.d(TAG, "onItemSelected: $position token :$token")
                            refreshButtonAndLayout(pendingAdapter)
                            lifecycleScope.launch {
                                observeNetwork(
                                    token,

                                    cursor,
                                    pendingAdapter

                                )
                            }

                        }
                        2 -> {
                            setItemAdapter(REJECTEDType)
                            Log.d(TAG, "onItemSelected: $position token :$token")
                            refreshButtonAndLayout(rejectAdapter)
                            lifecycleScope.launch {
                                observeNetwork(
                                    token,

                                    cursor,
                                    rejectAdapter

                                )

                            }

                        }
                        3 -> {
                            setItemAdapter(DELETEDType)
                            Log.d(TAG, "onItemSelected: $position token :$token")
                            refreshButtonAndLayout(deleteAdapter)
                            lifecycleScope.launch {
                                observeNetwork(
                                    token,

                                    cursor,
                                    deleteAdapter

                                )

                            }

                        }
                        else -> {
                            setItemAdapter(ACCEPTEDType)
                            Log.d(TAG, "onItemSelected: $position token :$token")
                            refreshButtonAndLayout(acceptAdapter)
                            lifecycleScope.launch {
                                observeNetwork(
                                    token,
                                    cursor,
                                    acceptAdapter
                                )
                            }
                        }
                    }
                }
            }

    }


    // paging에 먼저 피마리터 값 보내줌
    private fun observeGetData(token: String, cursor: String) {
        pagingViewModel.getData(token, cursor)
    }

    // paging 데이터값 받아옴
    @ExperimentalPagingApi
    private fun observePagingData(viewType: Int) {


        if (view != null) {
            when (viewType) {
                ACCEPTEDType -> {
                    lifecycleScope.launch {
                        pagingViewModel.acceptData.collectLatest {
                            acceptAdapter.submitData(it)
                        }


                    }

                }
                DELETEDType -> {
                    lifecycleScope.launch {
                        pagingViewModel.deleteData.collectLatest {
                            deleteAdapter.submitData(it)
                            Log.d(TAG, "getPost: $it")
                        }

                    }

                }
                PENDINGType -> {

                    lifecycleScope.launch {
                        pagingViewModel.pendingData.collectLatest {
                            pendingAdapter.submitData(it)
                        }


                    }

                }
                REJECTEDType -> {

                    lifecycleScope.launch {

                        pagingViewModel.rejectData.collectLatest {
                            rejectAdapter.submitData(it)
                            Log.d(TAG, "getPost: $it")
                        }

                    }


                }

            }

        }
    }


    @ExperimentalPagingApi
    fun observeNetwork(token: String, cursor: String, adapter: PagingDataAdapter<*, *>) {
        when (adapter) {
            acceptAdapter -> {
                // 데이터값을 보냄 (Paging x)
                observeGetData(token, cursor)
                observePagingData(ACCEPTEDType)


            }
            pendingAdapter -> {
                // 데이터값을 보냄 (Paging x)
                observeGetData(token, cursor)
                observePagingData(PENDINGType)
            }
            rejectAdapter -> {
                observeGetData(token, cursor)
                observePagingData(REJECTEDType)
            }
            deleteAdapter -> {
                observeGetData(token, cursor)
                observePagingData(DELETEDType)
            }

        }

    }

    private fun stateAdapter(adapter: PagingDataAdapter<*, *>) {

        when (adapter) {
            acceptAdapter -> {
                acceptAdapter.addLoadStateListener { combinedLoadStates ->


                        binding.errorRetryBtn.isVisible =
                            combinedLoadStates.source.refresh is LoadState.Error
                        binding.errorText.isVisible =
                            combinedLoadStates.source.refresh is LoadState.Error
                        binding.errorImg.isVisible =
                            combinedLoadStates.source.refresh is LoadState.Error
                        binding.postRecyclerView.isVisible =  combinedLoadStates.source.refresh is LoadState.Error
                        retrySetButton(acceptAdapter)




                    if (combinedLoadStates.source.refresh is LoadState.NotLoading
                        && combinedLoadStates.append.endOfPaginationReached
                        && acceptAdapter.itemCount < 1
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
            pendingAdapter -> {
                pendingAdapter.addLoadStateListener { combinedLoadStates ->

                    binding.errorRetryBtn.isVisible =
                        combinedLoadStates.source.refresh is LoadState.Error
                    binding.errorText.isVisible =
                        combinedLoadStates.source.refresh is LoadState.Error
                    binding.errorImg.isVisible =
                        combinedLoadStates.source.refresh is LoadState.Error
                    binding.postRecyclerView.isVisible =  combinedLoadStates.source.refresh is LoadState.Error
                    retrySetButton(pendingAdapter)

                    if (combinedLoadStates.source.refresh is LoadState.NotLoading
                        && combinedLoadStates.append.endOfPaginationReached
                        && pendingAdapter.itemCount < 1
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
            rejectAdapter -> {
                rejectAdapter.addLoadStateListener { combinedLoadStates ->

                    binding.errorRetryBtn.isVisible =
                        combinedLoadStates.source.refresh is LoadState.Error
                    binding.errorText.isVisible =
                        combinedLoadStates.source.refresh is LoadState.Error
                    binding.errorImg.isVisible =
                        combinedLoadStates.source.refresh is LoadState.Error
                    binding.postRecyclerView.isVisible =  combinedLoadStates.source.refresh is LoadState.Error
                    retrySetButton(rejectAdapter)

                    if (combinedLoadStates.source.refresh is LoadState.NotLoading
                        && combinedLoadStates.append.endOfPaginationReached
                        && rejectAdapter.itemCount < 1
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
            deleteAdapter -> {
                deleteAdapter.addLoadStateListener { combinedLoadStates ->
                    binding.errorRetryBtn.isVisible =
                        combinedLoadStates.source.refresh is LoadState.Error
                    binding.errorText.isVisible =
                        combinedLoadStates.source.refresh is LoadState.Error
                    binding.errorImg.isVisible =
                        combinedLoadStates.source.refresh is LoadState.Error
                    binding.postRecyclerView.isVisible =  combinedLoadStates.source.refresh is LoadState.Error
                    retrySetButton(deleteAdapter)

                    if (combinedLoadStates.source.refresh is LoadState.NotLoading
                        && combinedLoadStates.append.endOfPaginationReached
                        && deleteAdapter.itemCount < 1
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
            adapter.refresh()
        }
    }

    private fun setItemAdapter(viewType: Int) {


        val linearLayoutManagerWrapepr =
            context?.let { LinearLayoutManagerWrapper(it, LinearLayoutManager.VERTICAL, false) }



        when (viewType) {
            ACCEPTEDType -> {
                binding.postRecyclerView.apply {
                    this.adapter = acceptAdapter
                    this.adapter = acceptAdapter.withLoadStateHeaderAndFooter(
                        header = PostLoadingAdapter { acceptAdapter.retry() },
                        footer = PostLoadingAdapter { acceptAdapter.retry() },
                    )
                    this.layoutManager = linearLayoutManagerWrapepr
                }
                stateAdapter(acceptAdapter)
            }
            DELETEDType -> {
                binding.postRecyclerView.apply {
                    this.adapter = deleteAdapter
                    this.adapter = deleteAdapter.withLoadStateHeaderAndFooter(
                        header = PostLoadingAdapter { deleteAdapter.retry() },
                        footer = PostLoadingAdapter { deleteAdapter.retry() },

                        )
                    this.layoutManager = linearLayoutManagerWrapepr


                }
                stateAdapter(deleteAdapter)
            }
            REJECTEDType -> {
                binding.postRecyclerView.apply {
                    this.adapter = rejectAdapter
                    this.adapter = rejectAdapter.withLoadStateHeaderAndFooter(
                        header = PostLoadingAdapter { rejectAdapter.retry() },
                        footer = PostLoadingAdapter { rejectAdapter.retry() },
                    )
                    this.layoutManager = linearLayoutManagerWrapepr


                }
                stateAdapter(rejectAdapter)

            }
            PENDINGType -> {
                binding.postRecyclerView.apply {
                    this.adapter = pendingAdapter
                    this.adapter = pendingAdapter.withLoadStateHeaderAndFooter(
                        header = PostLoadingAdapter { pendingAdapter.retry() },
                        footer = PostLoadingAdapter { pendingAdapter.retry() },
                    )
                    this.layoutManager = linearLayoutManagerWrapepr
                }
                stateAdapter(pendingAdapter)
            }
        }
    }


}







