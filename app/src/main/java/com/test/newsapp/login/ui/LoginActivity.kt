package com.test.newsapp.login.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.test.newsapp.R
import com.test.newsapp.databinding.ActivityLoginBinding
import com.test.newsapp.login.model.LoginModel
import com.test.newsapp.news.ui.NewsActivity
import com.test.newsapp.utils.CommonUtils
import com.test.newsapp.utils.SessionManager


class LoginActivity : AppCompatActivity() {
    private lateinit var mBinding : ActivityLoginBinding
    private lateinit var mCommonUtils : CommonUtils
    private lateinit var mSessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intializeView()
        intializeMembers()
        handleClickListner()
    }

    private fun intializeView() {
        mBinding = ActivityLoginBinding.inflate(layoutInflater)
        val view = mBinding.root
        setContentView(view)
    }


    private fun handleClickListner() {
        mBinding.btnLogin.setOnClickListener {
         var mLoginModel = LoginModel(mBinding.etUserName.text.toString(), mBinding.etPassword.text.toString(),true)
            mSessionManager.setUserLoginDetailModel(mLoginModel)
            val intent : Intent = Intent(this,NewsActivity::class.java)
            startActivity(intent)
        }

    }

    private fun intializeMembers() {
        mSessionManager = SessionManager.getInstance(this@LoginActivity)!!

        mCommonUtils = CommonUtils()
    }


}