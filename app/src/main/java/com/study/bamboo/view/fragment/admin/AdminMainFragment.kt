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
import com.study.bamboo.LinearLayoutManagerWrapper
import com.study.bamboo.R
import com.study.bamboo.adapter.AdminHomeItemAdapter
import com.study.bamboo.adapter.AdminHomeItemAdapter.Companion.ACCEPTEDType
import com.study.bamboo.adapter.AdminHomeItemAdapter.Companion.DELETEDType
import com.study.bamboo.adapter.AdminHomeItemAdapter.Companion.PENDINGType
import com.study.bamboo.adapter.PostLoadingAdapter
import com.study.bamboo.databinding.FragmentAdminMainBinding
import com.study.bamboo.view.base.BaseFragment
import com.study.bamboo.view.fragment.admin.paging.PagingPostViewModel
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

    private val acceptAdapter: AdminHomeItemAdapter by lazy {
        AdminHomeItemAdapter(ACCEPTEDType)
    }

    private val deleteAdapter: AdminHomeItemAdapter by lazy {
        AdminHomeItemAdapter(DELETEDType)
    }

    private val rejectAdapter: AdminHomeItemAdapter by lazy {

        AdminHomeItemAdapter(AdminHomeItemAdapter.REJECTEDType)
    }

    private val pendingAdapter: AdminHomeItemAdapter by lazy {
        AdminHomeItemAdapter(PENDINGType)
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
                            setItemAdapter(acceptAdapter)
                            Log.d(TAG, "onItemSelected: $position token :$token")
                            lifecycleScope.launch {
                                observeNetwork(
                                    token,
                                    "",
                                    "ACCEPTED"
                                )
                            }

                        }
                        1 -> {
                            setItemAdapter(pendingAdapter)
                            Log.d(TAG, "onItemSelected: $position token :$token")

                            lifecycleScope.launch {
                                observeNetwork(
                                    token,

                                    "",
                                    "PENDING"
                                )
                            }

                        }
                        2 -> {
                            setItemAdapter(rejectAdapter)
                            Log.d(TAG, "onItemSelected: $position token :$token")

                            lifecycleScope.launch {
                                observeNetwork(
                                    token,

                                    "",
                                    "REJECTED"
                                )

                            }

                        }
                        3 -> {
                            setItemAdapter(deleteAdapter)
                            Log.d(TAG, "onItemSelected: $position token :$token")

                            lifecycleScope.launch {
                                observeNetwork(
                                    token,

                                    "",
                                    "DELETED"
                                )

                            }

                        }
                        else -> {
                            setItemAdapter(acceptAdapter)
                            Log.d(TAG, "onItemSelected: $position token :$token")

                            lifecycleScope.launch {
                                observeNetwork(
                                    token,
                                    "",
                                    "ACCEPTED"
                                )
                            }
                        }
                    }
                }
            }

    }


    private fun recyclerViewEnd(recyclerView: RecyclerView, adapter: AdminHomeItemAdapter) {
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
                } else {

                }
            }
        })
    }


    // Post observe (Paging x)
    private fun observeData(adapter: AdminHomeItemAdapter) {
        viewModel.getPostData.observe(viewLifecycleOwner, {
            adapter.setItemList(it)
        })
    }

    // paging에 먼저 피마리터 값 보내줌
    private fun observeGetData(token: String, cursor: String, status: String) {
        pagingViewModel.getData(token, cursor, status)
    }

    // paging 데이터값 받아옴
    private fun observePagingData(adapter: AdminHomeItemAdapter) {


        job = lifecycleScope.launch {

            pagingViewModel.flow.collectLatest {
                adapter.submitData( viewLifecycleOwner.lifecycle, it)
                Log.d(TAG, "getPost: $it")
            }

//            pagingViewModel.photos.observe(viewLifecycleOwner) {
//                adapter.submitData(viewLifecycleOwner.lifecycle, it)
//                Log.d(TAG, "getPost: $it")
//            }
        }

    }


    suspend fun observeNetwork(token: String, cursor: String, status: String) {
        when (status) {
            "ACCEPTED" -> {
                // 데이터값을 보냄 (Paging x)
                observeGetData(token, cursor, status)


//                //먼저 데이터값을 adapter에 넣음
//                observeData(acceptAdapter)
//                viewModel.getPost(token, 20, cursor, status)



                observePagingData(acceptAdapter)
                recyclerViewEnd(binding.postRecyclerView,acceptAdapter)
                // 잘 넣어지면 게시물의 position 이 마지막으로 되면 페이징 으로 데이터를 보냄
//                recyclerViewEnd(binding.postRecyclerView, acceptAdapter)

            }
            "PENDING" -> {
                // 데이터값을 보냄 (Paging x)
                observeGetData(token, cursor, status)
                observePagingData(pendingAdapter)
            }
            "REJECTED" -> {
                observeGetData(token, cursor, status)
                observePagingData(rejectAdapter)
            }
            "DELETED" -> {
                observeGetData(token, cursor, status)
                observePagingData(deleteAdapter)
            }

        }

    }


    private fun setItemAdapter(adapter: AdminHomeItemAdapter) {


        val linearLayoutManagerWrapepr =
            context?.let { LinearLayoutManagerWrapper(it, LinearLayoutManager.VERTICAL, false) }


        binding.postRecyclerView.apply {
            this.adapter = adapter
            this.adapter = adapter.withLoadStateHeaderAndFooter(
                header = PostLoadingAdapter { adapter.retry() },
                footer = PostLoadingAdapter { adapter.retry() }
            )
            this.layoutManager = linearLayoutManagerWrapepr
        }
    }
}

