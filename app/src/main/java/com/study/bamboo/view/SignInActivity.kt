package com.study.bamboo.view

import android.content.Context
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.study.bamboo.R
import com.study.bamboo.databinding.ActivityMainBinding
import com.study.bamboo.databinding.ActivitySignInBinding
import com.study.bamboo.utils.ViewModel
import com.study.bamboo.view.base.BaseActivity
import com.study.bamboo.view.dialog.LoginDialog
import com.study.bamboo.viewmodel.SignInViewModel

class SignInActivity : BaseActivity() {
    private val binding by binding<ActivitySignInBinding>(R.layout.activity_sign_in)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        binding.activity = this

        ViewModel.signInViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(SignInViewModel::class.java)


    }


    fun clickUserLogin(view: View){
        val dialog = LoginDialog()
        dialog.show(supportFragmentManager, "UserLoginDialog")
        //dialog.dialog?.window?.setBackgroundDrawableResource(R.drawable.popup_press)

        val windowManager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)

        ViewModel.signInViewModel.display_size_x.value = size.x


        //dialog.dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
    }

    fun clickAdminLogin(view: View){
        val dialog = LoginDialog()
        dialog.show(supportFragmentManager, "AdminLoginDialog")
    }

}