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
import com.study.bamboo.R
import com.study.bamboo.adapter.AdminHomeItemAdapter
import com.study.bamboo.adapter.Status
import com.study.bamboo.databinding.FragmentAdminMainBinding
import com.study.bamboo.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AdminMainFragment : BaseFragment<FragmentAdminMainBinding>(R.layout.fragment_admin_main) {

    companion object {
        const val TAG = "AdminMainFragment"
    }

    private lateinit var viewModel: AdminViewModel

    private val acceptAdapter: AdminHomeItemAdapter by lazy {
        AdminHomeItemAdapter(Status.ACCEPTED)
    }

    private val deleteAdapter: AdminHomeItemAdapter by lazy {
        AdminHomeItemAdapter(Status.DELETED)
    }

    private val rejectAdapter: AdminHomeItemAdapter by lazy {

        AdminHomeItemAdapter(Status.REJECTED)
    }

    private val pendingAdapter: AdminHomeItemAdapter by lazy {
        AdminHomeItemAdapter(Status.PENDING)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(AdminViewModel::class.java)
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
        setItemAdapter(acceptAdapter)
        setItemAdapter(pendingAdapter)
        setItemAdapter(rejectAdapter)
        setItemAdapter(deleteAdapter)

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
                    Log.d(TAG, "onItemSelected: $token")
                    when (position) {
                        0 -> {


                            lifecycleScope.launch {
                                observeNetwork(
                                    token,
                                    20,
                                    "60b8407473d81a1b4cc591a5",
                                    "ACCEPTED"
                                )
                            }

                        }
                        1 -> {

                            lifecycleScope.launch {
                                observeNetwork(
                                    token,
                                    20,
                                    "60b8407473d81a1b4cc591a5",
                                    "PENDING"
                                )
                            }

                        }
                        2 -> {

                            lifecycleScope.launch {
                                observeNetwork(
                                    token,
                                    20,
                                    "60b8407473d81a1b4cc591a5",
                                    "REJECTED"
                                )

                            }

                        }
                        3 -> {

                            lifecycleScope.launch {
                                observeNetwork(
                                    token,
                                    20,
                                    "60b8407473d81a1b4cc591a5",
                                    "DELETED"
                                )

                            }

                        }
                        else -> {
                        }
                    }
                }
            }

    }


    suspend fun observeNetwork(token: String?, count: Int, cursor: String, status: String) {

        when (status) {
            "ACCEPTED" -> lifecycleScope.launch {
                viewModel.getPostData(token.toString(), count, cursor, status)
                // status에 따라 post 가져옴
                observeGetPost(acceptAdapter)

            }
            "PENDING" -> lifecycleScope.launch {

                viewModel.getPostData(token.toString(), count, cursor, status)
                observeGetPost(pendingAdapter)


            }
            "REJECTED" -> lifecycleScope.launch {

                viewModel.getPostData(token.toString(), count, cursor, status)
                observeGetPost(rejectAdapter)

            }
            "DELETED" -> lifecycleScope.launch {
                viewModel.getPostData(token.toString(), count, cursor, status)
                observeGetPost(deleteAdapter)

            }

        }

    }


    private fun observeGetPost(adapter: AdminHomeItemAdapter) {
        viewModel.getPostData.observe(viewLifecycleOwner, {
            adapter.setItemList(it)


        })
    }


    private fun setItemAdapter(adapter: AdminHomeItemAdapter) {
        binding.postRecyclerView.apply {
            this.adapter = adapter
            this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }
}

