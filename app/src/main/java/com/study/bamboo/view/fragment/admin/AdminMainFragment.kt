package com.study.bamboo.view.fragment.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import carbon.recycler.Adapter
import com.study.bamboo.R
import com.study.bamboo.databinding.FragmentAdminMainBinding
import com.study.bamboo.utils.Functions
import com.study.bamboo.adapter.AdminHomeItemAdapter
import com.study.bamboo.adapter.Situation
import com.study.bamboo.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AdminMainFragment : BaseFragment<FragmentAdminMainBinding>(R.layout.fragment_admin_main) {


    private val viewModel: AdminViewModel by viewModels()


    private val acceptAdapter: AdminHomeItemAdapter by lazy {

        AdminHomeItemAdapter(Situation.ACCEPT)
    }
    private val deleteAdapter: AdminHomeItemAdapter by lazy {

        AdminHomeItemAdapter(Situation.DELETE)
    }
    private val rejectAdapter: AdminHomeItemAdapter by lazy {

        AdminHomeItemAdapter(Situation.REJECT)
    }
    private val waitingAdapter: AdminHomeItemAdapter by lazy {

        AdminHomeItemAdapter(Situation.WAITING)
    }

    override fun FragmentAdminMainBinding.onCreateView() {

        spinnerContact()
    }

    override fun FragmentAdminMainBinding.onViewCreated() {
        //spinner
        binding.activitySpinner.adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.AdminItemList,
            R.layout.admin_spinner_item
        )

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
                            observeNetwork(20, "", "")
                            viewModel.getPostData.observe(viewLifecycleOwner, {

                                acceptAdapter.setItemList(listOf(it))
                            })

                        }
                        1 -> {
                            setItemAdapter(waitingAdapter)
                            viewModel.getPostData.observe(viewLifecycleOwner, {
                                waitingAdapter.setItemList(listOf(it))

                            })
                        }
                        2 -> {
                            setItemAdapter(rejectAdapter)
                            viewModel.getPostData.observe(viewLifecycleOwner, {

                                rejectAdapter.setItemList(listOf(it))
                            })
                        }
                        3 -> {

                            setItemAdapter(deleteAdapter)
                            viewModel.getPostData.observe(viewLifecycleOwner, {
                                deleteAdapter.setItemList(listOf(it))

                            })

                        }
                        else -> {
                        }
                    }
                }
            }

    }

    fun observeNetwork(count: Int, cursor: String, status: String) {
        viewModel.getPost(count, cursor, status)
    }

    fun setItemAdapter(adapter: AdminHomeItemAdapter) {
        binding.postRecyclerView.apply {
            this.adapter = adapter
            this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }
}