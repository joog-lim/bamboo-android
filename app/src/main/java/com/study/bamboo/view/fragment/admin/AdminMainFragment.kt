package com.study.bamboo.view.fragment.admin

import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope

import androidx.recyclerview.widget.LinearLayoutManager
import com.study.bamboo.R
import com.study.bamboo.adapter.AdminHomeItemAdapter
import com.study.bamboo.adapter.Situation
import com.study.bamboo.databinding.FragmentAdminMainBinding
import com.study.bamboo.utils.ViewModel.signInViewModel
import com.study.bamboo.view.activity.signin.SignInViewModel
import com.study.bamboo.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AdminMainFragment : BaseFragment<FragmentAdminMainBinding>(R.layout.fragment_admin_main) {

    companion object {
        const val TAG = "AdminMainFragment"
    }


    private val viewModel: AdminViewModel by viewModels()
    private val tokenViewModel: SignInViewModel by viewModels()

    private val acceptAdapter: AdminHomeItemAdapter by lazy {

        AdminHomeItemAdapter(Situation.ACCEPT)

    }
    private val deleteAdapter: AdminHomeItemAdapter by lazy {

        AdminHomeItemAdapter(Situation.DELETE)

    }
    private val rejectAdapter: AdminHomeItemAdapter by lazy {

        AdminHomeItemAdapter(Situation.REJECT)

    }
    private val pendingAdapter: AdminHomeItemAdapter by lazy {

        AdminHomeItemAdapter(Situation.WAITING)

    }
    var token=""

    override fun FragmentAdminMainBinding.onCreateView() {
        //spinner
        binding.activitySpinner.adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.AdminItemList,
            R.layout.admin_spinner_item
        )
        spinnerContact()

        observeCreateToken()

    }

    override fun FragmentAdminMainBinding.onViewCreated() {

//        Adapter 연결
        setItemAdapter(acceptAdapter)
        setItemAdapter(rejectAdapter)
        setItemAdapter(deleteAdapter)
        setItemAdapter(pendingAdapter)

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

                    observeReturnToken()
                    when (position) {
                        0 -> {

                            lifecycleScope.launch {
                                observeNetwork(token, 20, "60b8407473d81a1b4cc591a5", "ACCEPTED")

                            }

                        }
                        1 -> {


                            lifecycleScope.launch {
                                observeNetwork(token, 20, "60b8407473d81a1b4cc591a5", "PENDING")
                            }

                        }
                        2 -> {

                            lifecycleScope.launch {
                                observeNetwork(token, 20, "60b8407473d81a1b4cc591a5", "REJECTED")

                            }

                        }
                        3 -> {

                            lifecycleScope.launch {
                                observeNetwork(token, 20, "60b8407473d81a1b4cc591a5", "DELETED")

                            }

                        }
                        else -> {
                        }
                    }
                }
            }

    }

     private fun observeCreateToken(){
        lifecycleScope.launch {
            tokenViewModel.callAdminLoginAPI("#promotion")

        }
    }
    fun observeReturnToken() : String{

        tokenViewModel.adminLoginResponse.observe(requireActivity(), {

            if (token.isEmpty()) {
                token = it
                Log.d(TAG, "onItemSelected: $token")
            }
        })
        return token

    }

    suspend fun observeNetwork(token: String?, count: Int, cursor: String, status: String) {

            when (status) {
                "ACCEPTED" -> lifecycleScope.launch {
                    viewModel.getPostData(token.toString(),count, cursor, status)
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
    private  fun observeGetPost(adapter:AdminHomeItemAdapter){
        viewModel.getPostData.observe(viewLifecycleOwner,{
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
