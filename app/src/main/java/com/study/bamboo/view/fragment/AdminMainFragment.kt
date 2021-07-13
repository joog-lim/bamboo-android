package com.study.bamboo.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.study.bamboo.R
import com.study.bamboo.databinding.FragmentAdminMainBinding
import com.study.bamboo.utils.Functions
import com.study.bamboo.view.adapter.AdminHomeItemAdapter
import com.study.bamboo.view.adapter.UserHomeItemAdapter


class AdminMainFragment : Fragment() {

    lateinit var binding : FragmentAdminMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_admin_main,container,false)
        Functions.recyclerViewManager(binding.postRecyclerView,requireContext())
        binding.postRecyclerView.adapter = AdminHomeItemAdapter()

        //spinner
        binding.activitySpinner.adapter = ArrayAdapter.createFromResource(requireContext(),R.array.AdminItemList, R.layout.admin_spinner_item)


        binding.activitySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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

                    }
                    1 -> {

                    }
                    2 -> {

                    }
                    3 -> {

                    }
                    else -> {
                    }
                }
            }
        }


        return binding.root
    }
}