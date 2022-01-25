package com.test.newsapp.login.ui

import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.test.newsapp.R
import com.test.newsapp.databinding.ActivityLoginBinding
import com.test.newsapp.login.model.LoginModel
import com.test.newsapp.utils.CommonUtils
import com.test.newsapp.utils.SessionManager


class LoginActivity : AppCompatActivity() {
    private lateinit var mBinding : ActivityLoginBinding
    private lateinit var mCommonUtils : CommonUtils
    private lateinit var mSessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intializeMembers()
        handleClickListner()
    }




    private fun handleClickListner() {
        mBinding.btnLogin.setOnClickListener {
         var mLoginModel = LoginModel(mBinding.etUserName.text.toString(), mBinding.etPassword.text.toString(),true)



            mSessionManager.setUserLoginDetailModel(mLoginModel)
        }

    }

    private fun intializeMembers() {
        mSessionManager = SessionManager.getInstance(this@LoginActivity)!!
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        mCommonUtils = CommonUtils()
    }


}