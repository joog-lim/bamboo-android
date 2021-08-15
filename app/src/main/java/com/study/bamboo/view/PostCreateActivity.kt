package com.study.bamboo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.study.bamboo.R
import com.study.bamboo.databinding.ActivityMainBinding
import com.study.bamboo.databinding.ActivityPostCreateBinding
import com.study.bamboo.view.base.BaseActivity

class PostCreateActivity : BaseActivity() {

    private val binding by binding<ActivityPostCreateBinding>(R.layout.activity_post_create)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_create)


        //spinner
        binding.choiceTag.adapter = ArrayAdapter.createFromResource(this,R.array.PostCreateTagList, R.layout.post_create_tag_spinner_item)


        binding.choiceTag.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
                    4 -> {

                    }
                    5 -> {

                    }
                    6 -> {

                    }
                    7 -> {

                    }
                    else -> {
                    }
                }
            }
        }

/*        setupSpinnerTag()
        setupSpinnerHandler()*/
    }

    private fun setupSpinnerTag() {
        val tags = resources.getStringArray(R.array.PostCreateTagList)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tags)
        binding.choiceTag.adapter = adapter

    }

    private fun setupSpinnerHandler() {
        binding.choiceTag.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
                    4 -> {

                    }
                    5 -> {

                    }
                    6 -> {

                    }
                    7 -> {

                    }
                    else -> {
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

    }

}