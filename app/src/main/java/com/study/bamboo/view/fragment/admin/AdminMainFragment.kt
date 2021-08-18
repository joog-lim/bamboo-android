package com.study.bamboo.view.fragment.admin

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter

import androidx.recyclerview.widget.LinearLayoutManager
import com.study.bamboo.R
import com.study.bamboo.databinding.FragmentAdminMainBinding
import com.study.bamboo.adapter.AdminHomeItemAdapter
import com.study.bamboo.adapter.Situation
import com.study.bamboo.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AdminMainFragment : BaseFragment<FragmentAdminMainBinding>(R.layout.fragment_admin_main) {

    companion object {
        const val TAG = "AdminMainFragment"
    }

    /*private val viewModel: AdminViewModel by viewModels()
    private val tokenViewModel: SignInViewModel by viewModels()
*/
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
        setItemAdapter(acceptAdapter)
        spinnerContact()
        //spinner
        binding.activitySpinner.adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.AdminItemList,
            R.layout.admin_spinner_item
        )
    }

    override fun FragmentAdminMainBinding.onViewCreated() {


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
              /*      when (position) {
                        0 -> {

                            setItemAdapter(acceptAdapter)
                            observeNetwork(
                                tokenViewModel.adminLoginResponse.toString(),
                                20,
                                "60b8407473d81a1b4cc591a5",
                                "ACCEPTED"
                            )
                            viewModel.getPostData.observe(viewLifecycleOwner, {

                                Log.d(TAG, "data: $it")
                                acceptAdapter.setItemList(it!!)
                            })

                        }
                        1 -> {

                            setItemAdapter(waitingAdapter)
                            observeNetwork( tokenViewModel.adminLoginResponse.toString(),20, "60b8407473d81a1b4cc591a5", "PENDING")
                            viewModel.getPostData.observe(viewLifecycleOwner, {
                                waitingAdapter.setItemList(it!!)

                            })
                        }
                        2 -> {
                            setItemAdapter(rejectAdapter)
                            observeNetwork( tokenViewModel.adminLoginResponse.toString(),20, "60b8407473d81a1b4cc591a5", "REJECT")

                            viewModel.getPostData.observe(viewLifecycleOwner, {


                                rejectAdapter.setItemList(it!!)
                            })
                        }
                        3 -> {
                            setItemAdapter(deleteAdapter)
                            observeNetwork( tokenViewModel.adminLoginResponse.toString(),20, "60b8407473d81a1b4cc591a5", "DELETED")

                            viewModel.getPostData.observe(viewLifecycleOwner, {
                                Log.d("DELETE", "onItemSelected: $it ")
                                deleteAdapter.setItemList(it!!)

                            })

                        }
                        else -> {
                        }
                    }*/
                }
            }

    }

/*    fun observeNetwork(token: String, count: Int, cursor: String, status: String) =
        lifecycleScope.launch {
            viewModel.getPost(token, count, cursor, status)

        }*/

    fun setItemAdapter(adapter: AdminHomeItemAdapter) {
        binding.postRecyclerView.apply {
            this.adapter = adapter
            this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

}
