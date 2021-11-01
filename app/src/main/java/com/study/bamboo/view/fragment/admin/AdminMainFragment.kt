package com.study.bamboo.view.fragment.admin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.IdRes
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.study.bamboo.R
import com.study.bamboo.adapter.PostLoadingAdapter
import com.study.bamboo.adapter.admin.AdminAdapter
import com.study.bamboo.adapter.admin.AdminAdapter.Companion.ACCEPTED
import com.study.bamboo.adapter.admin.AdminAdapter.Companion.ACCEPTEDType
import com.study.bamboo.adapter.admin.AdminAdapter.Companion.DELETEDType
import com.study.bamboo.adapter.admin.AdminAdapter.Companion.PENDINGType
import com.study.bamboo.adapter.admin.AdminAdapter.Companion.REJECTEDType
import com.study.bamboo.base.BaseFragment
import com.study.bamboo.data.network.models.user.UserPostDTOBase
import com.study.bamboo.data.paging.viewModel.PagingPostViewModel
import com.study.bamboo.databinding.FragmentAdminMainBinding
import com.study.bamboo.util.LinearLayoutManagerWrapper
import com.study.bamboo.util.Util.Companion.DEFAULT_TOKEN
import com.study.bamboo.util.Util.Companion.DIALOG_RESULT_KEY
import com.study.bamboo.view.activity.main.MainActivity
import com.study.bamboo.view.activity.signin.LoginDialog
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


    private val adminAdapter: AdminAdapter by lazy { AdminAdapter() }


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


        binding.activitySpinner.adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.AdminItemList,
            R.layout.admin_spinner_item
        )

        observeUiPreferences()

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


    private fun observeUiPreferences() {
        viewModel.readToken.asLiveData().observe(viewLifecycleOwner, {
            token = it.token
            tokenNull(token)
            Log.d(TAG, "observeUiPreferences: ${it.token}")

        })

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


    // paging에 먼저 피마리터 값 보내줌
    private fun observeGetData(token: String, cursor: String) {
        pagingViewModel.getData(token, cursor)
    }

    private fun tokenNull(token: String) {
        val loginDialog = LoginDialog()
        if (token == DEFAULT_TOKEN)
            loginDialog.show(childFragmentManager, "AdminMainFragment")

    }


    private fun onClickUser() {
        binding.userText.setOnClickListener {
            val intent: Intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }
    }

    // paging 데이터값 받아옴
    @ExperimentalPagingApi
    private fun observePagingData(viewType: Int) {


        if (view != null) {
            when (viewType) {
                ACCEPTEDType -> {
                    lifecycleScope.launch {
                        pagingViewModel.acceptData.collectLatest {
                            adminAdapter.submitData(it as PagingData<UserPostDTOBase>)
                        }


                    }

                }
                DELETEDType -> {
                    lifecycleScope.launch {
                        pagingViewModel.deleteData.collectLatest {
                            adminAdapter.submitData(it as PagingData<UserPostDTOBase>)
                            Log.d(TAG, "getPost: $it")
                        }

                    }

                }
                PENDINGType -> {

                    lifecycleScope.launch {
                        pagingViewModel.pendingData.collectLatest {
                            adminAdapter.submitData(it as PagingData<UserPostDTOBase>)
                        }


                    }

                }
                REJECTEDType -> {

                    lifecycleScope.launch {

                        pagingViewModel.rejectData.collectLatest {
                            adminAdapter.submitData(it as PagingData<UserPostDTOBase>)
                            Log.d(TAG, "getPost: $it")
                        }

                    }


                }

            }

        }
    }


    @ExperimentalPagingApi
    fun observeNetwork(viewType: Int) {

        // 데이터값을 보냄 (Paging x)
        observeGetData(token, cursor)
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

}
