package com.study.bamboo.view.fragment.admin

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.study.bamboo.utils.LinearLayoutManagerWrapper
import com.study.bamboo.R
import com.study.bamboo.adapter.admin.AdminAcceptAdapter
import com.study.bamboo.adapter.admin.AdminAcceptAdapter.Companion.ACCEPTED
import com.study.bamboo.adapter.admin.AdminAcceptAdapter.Companion.ACCEPTEDType
import com.study.bamboo.adapter.admin.AdminAcceptAdapter.Companion.DELETED
import com.study.bamboo.adapter.admin.AdminAcceptAdapter.Companion.DELETEDType
import com.study.bamboo.adapter.admin.AdminAcceptAdapter.Companion.PENDING
import com.study.bamboo.adapter.admin.AdminAcceptAdapter.Companion.PENDINGType
import com.study.bamboo.adapter.admin.AdminAcceptAdapter.Companion.REJECTED
import com.study.bamboo.adapter.admin.AdminAcceptAdapter.Companion.REJECTEDType
import com.study.bamboo.adapter.PostLoadingAdapter
import com.study.bamboo.adapter.admin.AdminDeleteAdapter
import com.study.bamboo.adapter.admin.AdminPendingAdapter
import com.study.bamboo.adapter.admin.AdminRejectAdapter
import com.study.bamboo.databinding.FragmentAdminMainBinding
import com.study.bamboo.base.BaseFragment
import com.study.bamboo.view.fragment.admin.paging.viewModel.PagingPostViewModel
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

    private lateinit var viewModel: AdminViewModel
    private lateinit var pagingViewModel: PagingPostViewModel

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
    private var job: Job? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(AdminViewModel::class.java)
        pagingViewModel = ViewModelProvider(requireActivity()).get(PagingPostViewModel::class.java)
    }

    var token = ""
    override fun FragmentAdminMainBinding.onCreateView() {
        binding.activitySpinner.adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.AdminItemList,
            R.layout.admin_spinner_item
        )

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
                            lifecycleScope.launch {
                                observeNetwork(
                                    token,
                                    "",
                                    ACCEPTED
                                )
                            }

                        }
                        1 -> {
                            setItemAdapter(PENDINGType)
                            Log.d(TAG, "onItemSelected: $position token :$token")

                            lifecycleScope.launch {
                                observeNetwork(
                                    token,

                                    "",
                                    PENDING
                                )
                            }

                        }
                        2 -> {
                            setItemAdapter(REJECTEDType)
                            Log.d(TAG, "onItemSelected: $position token :$token")

                            lifecycleScope.launch {
                                observeNetwork(
                                    token,

                                    "",
                                    REJECTED

                                )

                            }

                        }
                        3 -> {
                            setItemAdapter(DELETEDType)
                            Log.d(TAG, "onItemSelected: $position token :$token")

                            lifecycleScope.launch {
                                observeNetwork(
                                    token,

                                    "",
                                    DELETED

                                )

                            }

                        }
                        else -> {
                            setItemAdapter(ACCEPTEDType)
                            Log.d(TAG, "onItemSelected: $position token :$token")

                            lifecycleScope.launch {
                                observeNetwork(
                                    token,
                                    "",
                                    ACCEPTED
                                )
                            }
                        }
                    }
                }
            }

    }


    private fun recyclerViewEnd(recyclerView: RecyclerView, adapter: AdminAcceptAdapter) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)


                val lastVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition() // 화면에 보이는 마지막 아이템의 position
                val itemTotalCount = recyclerView.adapter!!.itemCount - 1 // 어댑터에 등록된 아이템의 총 개수 -1

                Log.d(TAG, "현재 포지션: $lastVisibleItemPosition")

                // 스크롤이 끝에 도달했는지 확인
                if (lastVisibleItemPosition == itemTotalCount) {
                    Log.d(TAG, "onScrolled: 마지막 포지션: $lastVisibleItemPosition")
                }
            }
        })
    }


    // paging에 먼저 피마리터 값 보내줌
    private fun observeGetData(token: String, cursor: String, status: String) {
        pagingViewModel.getData(token, cursor, status)
    }

    // paging 데이터값 받아옴
    private fun observePagingData(viewType:Int) {

        job = lifecycleScope.launch {

            when(viewType){
                ACCEPTEDType->{
                    pagingViewModel.acceptData.collectLatest {
                        acceptAdapter.submitData(viewLifecycleOwner.lifecycle, it)
                        Log.d(TAG, "getPost: $it")
                    }
                }
                DELETEDType->{
                    pagingViewModel.deleteData.collectLatest {

                        deleteAdapter.submitData(viewLifecycleOwner.lifecycle, it)
                        Log.d(TAG, "getPost: $it")
                    }

                }
                PENDINGType->{
                    pagingViewModel.pendingData.collectLatest {


                        pendingAdapter.submitData(viewLifecycleOwner.lifecycle, it)
                        Log.d(TAG, "getPost: $it")

                    }

                }
                REJECTEDType->{
                    pagingViewModel.rejectData.collectLatest {
                        rejectAdapter.submitData(viewLifecycleOwner.lifecycle, it)
                        Log.d(TAG, "getPost: $it")
                    }
                }

            }


        }
    }


    suspend fun observeNetwork(token: String, cursor: String, status: String) {
        when (status) {
            ACCEPTED -> {
                // 데이터값을 보냄 (Paging x)
                observeGetData(token, cursor, status)
                observePagingData(ACCEPTEDType)
                recyclerViewEnd(binding.postRecyclerView, acceptAdapter)


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






