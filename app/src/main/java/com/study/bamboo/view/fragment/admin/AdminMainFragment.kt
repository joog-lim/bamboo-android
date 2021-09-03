package com.study.bamboo.view.fragment.admin

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.study.bamboo.R
import com.study.bamboo.adapter.PostLoadingAdapter
import com.study.bamboo.adapter.admin.AdminAcceptAdapter
import com.study.bamboo.adapter.admin.AdminAcceptAdapter.Companion.ACCEPTED
import com.study.bamboo.adapter.admin.AdminAcceptAdapter.Companion.ACCEPTEDType
import com.study.bamboo.adapter.admin.AdminAcceptAdapter.Companion.DELETED
import com.study.bamboo.adapter.admin.AdminAcceptAdapter.Companion.DELETEDType
import com.study.bamboo.adapter.admin.AdminAcceptAdapter.Companion.PENDING
import com.study.bamboo.adapter.admin.AdminAcceptAdapter.Companion.PENDINGType
import com.study.bamboo.adapter.admin.AdminAcceptAdapter.Companion.REJECTED
import com.study.bamboo.adapter.admin.AdminAcceptAdapter.Companion.REJECTEDType
import com.study.bamboo.adapter.admin.AdminDeleteAdapter
import com.study.bamboo.adapter.admin.AdminPendingAdapter
import com.study.bamboo.adapter.admin.AdminRejectAdapter
import com.study.bamboo.base.BaseFragment
import com.study.bamboo.data.paging.viewModel.PagingPostViewModel
import com.study.bamboo.databinding.FragmentAdminMainBinding
import com.study.bamboo.utils.LinearLayoutManagerWrapper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

// TODO: 2021-08-26


@AndroidEntryPoint
class AdminMainFragment : BaseFragment<FragmentAdminMainBinding>(R.layout.fragment_admin_main) {

    companion object {
        const val TAG = "AdminMainFragment"


    }

    private var firstStart = true


    override fun onResume() {
        super.onResume()
        if (firstStart) {
            binding.progressBar.visibility = View.VISIBLE
            firstStart = false
        } else {
            binding.progressBar.visibility = View.GONE
        }
        Log.d("onResume", "onResume")
    }


    override fun onStop() {
        super.onStop()
        binding.progressBar.visibility = View.GONE
    }

    private val viewModel: AdminViewModel by viewModels()
    private lateinit var pagingViewModel: PagingPostViewModel


    private  val acceptAdapter: AdminAcceptAdapter by lazy{
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
    private var job: Job? = null

    var status: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pagingViewModel = ViewModelProvider(requireActivity()).get(PagingPostViewModel::class.java)

    }

    var token = ""
    var cursor = ""

    @ExperimentalPagingApi
    override fun FragmentAdminMainBinding.onCreateView() {
        binding.activitySpinner.adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.AdminItemList,
            R.layout.admin_spinner_item
        )




        binding.refreshLayout.setOnRefreshListener{
            acceptAdapter.refresh()
            rejectAdapter.refresh()
            refreshLayout.isRefreshing = false
        }

        binding.updateBtn.setOnClickListener{
            acceptAdapter.refresh()
        }
        observeUiPreferences()
        spinnerContact()
        setItemAdapter(ACCEPTEDType)


    }


    override fun FragmentAdminMainBinding.onViewCreated() {
    }


    private fun observeUiPreferences() {
        viewModel.readToken.asLiveData().observe(viewLifecycleOwner, {
            token = it.token
            Log.d(TAG, "observeUiPreferences: ${it.token}")

        })

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
                            acceptAdapter.refresh()
                            Log.d(TAG, "onItemSelected: $position token :$token")

                            lifecycleScope.launch {
                                observeNetwork(
                                    token,
                                    cursor,
                                    ACCEPTED
                                )
                            }

                        }
                        1 -> {
                            setItemAdapter(PENDINGType)
                            pendingAdapter.refresh()
                            Log.d(TAG, "onItemSelected: $position token :$token")
                            lifecycleScope.launch {
                                observeNetwork(
                                    token,

                                    cursor,
                                    PENDING
                                )
                            }

                        }
                        2 -> {
                            setItemAdapter(REJECTEDType)
                            rejectAdapter.refresh()
                            Log.d(TAG, "onItemSelected: $position token :$token")
                            lifecycleScope.launch {
                                observeNetwork(
                                    token,

                                    cursor,
                                    REJECTED

                                )

                            }

                        }
                        3 -> {
                            setItemAdapter(DELETEDType)
                            deleteAdapter.refresh()
                            Log.d(TAG, "onItemSelected: $position token :$token")
                            lifecycleScope.launch {
                                observeNetwork(
                                    token,

                                    cursor,
                                    DELETED

                                )

                            }

                        }
                        else -> {
                            setItemAdapter(ACCEPTEDType)
                            acceptAdapter.refresh()
                            Log.d(TAG, "onItemSelected: $position token :$token")
                            lifecycleScope.launch {
                                observeNetwork(
                                    token,
                                    cursor,
                                    ACCEPTED
                                )
                            }
                        }
                    }
                }
            }

    }





    // paging에 먼저 피마리터 값 보내줌
    private fun observeGetData(token: String, cursor: String, status: String) {
        pagingViewModel.getData(token, cursor, status)
    }

    // paging 데이터값 받아옴
    @ExperimentalPagingApi
    private fun observePagingData(viewType: Int) {


        if (view != null) {
            when (viewType) {
                ACCEPTEDType -> {
                    job = lifecycleScope.launch {
                        pagingViewModel.acceptData.collectLatest {
                            acceptAdapter.submitData(it)
                        }


                    }

                }
                DELETEDType -> {
                    job = lifecycleScope.launch {
                        pagingViewModel.deleteData.collectLatest {
                            deleteAdapter.submitData(it)
                            Log.d(TAG, "getPost: $it")
                        }

                    }

                }
                PENDINGType -> {

                    job = lifecycleScope.launch {
                        pagingViewModel.pendingData.collectLatest {
                            pendingAdapter.submitData(it)
                        }


                    }

                }
                REJECTEDType -> {

                    job = lifecycleScope.launch {

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
    suspend fun observeNetwork(token: String, cursor: String, status: String) {
        when (status) {
            ACCEPTED -> {
                // 데이터값을 보냄 (Paging x)
                observeGetData(token, cursor, status)
                observePagingData(ACCEPTEDType)


            }
            PENDING -> {
                // 데이터값을 보냄 (Paging x)
                observeGetData(token, cursor, status)
                observePagingData(PENDINGType)
            }
            REJECTED -> {
                observeGetData(token, cursor, status)
                observePagingData(REJECTEDType)
            }
            DELETED -> {
                observeGetData(token, cursor, status)
                observePagingData(DELETEDType)
            }

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
                        footer = PostLoadingAdapter { acceptAdapter.retry() }
                    )
                    this.layoutManager = linearLayoutManagerWrapepr


                }
            }
            DELETEDType -> {
                binding.postRecyclerView.apply {
                    this.adapter = deleteAdapter
                    this.adapter = deleteAdapter.withLoadStateHeaderAndFooter(
                        header = PostLoadingAdapter { deleteAdapter.retry() },
                        footer = PostLoadingAdapter { deleteAdapter.retry() }
                    )
                    this.layoutManager = linearLayoutManagerWrapepr


                }

            }
            REJECTEDType -> {
                binding.postRecyclerView.apply {
                    this.adapter = rejectAdapter
                    this.adapter = rejectAdapter.withLoadStateHeaderAndFooter(
                        header = PostLoadingAdapter { rejectAdapter.retry() },
                        footer = PostLoadingAdapter { rejectAdapter.retry() }
                    )
                    this.layoutManager = linearLayoutManagerWrapepr


                }

            }
            PENDINGType -> {
                binding.postRecyclerView.apply {
                    this.adapter = pendingAdapter
                    this.adapter = pendingAdapter.withLoadStateHeaderAndFooter(
                        header = PostLoadingAdapter { pendingAdapter.retry() },
                        footer = PostLoadingAdapter { pendingAdapter.retry() }
                    )
                    this.layoutManager = linearLayoutManagerWrapepr


                }
            }
        }
    }


}







