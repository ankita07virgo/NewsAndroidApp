package com.test.newsapp.login.ui

import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.test.newsapp.R
import com.test.newsapp.databinding.SplashMainBinding
import com.test.newsapp.login.viewmodel.SplashViewModel
import com.test.newsapp.news.ui.NewsActivity
import com.test.newsapp.utils.CommonUtils
import com.test.newsapp.utils.SessionManager
import java.util.*


class SplashActivity : AppCompatActivity() {
    private lateinit var mBinding: SplashMainBinding
    private lateinit var mSplashViewModel: SplashViewModel
    private  var mSessionManager: SessionManager? = null
    private lateinit var mCommonUtils: CommonUtils
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intializeMembers()
        handleObservers()
        mSplashViewModel.checkSplashActivityState()


    }

    private fun handleObservers() {
        mSplashViewModel.getSplashViewState()?.observe(this, Observer {
            if (it == null)
                return@Observer
            if( mSessionManager?.getUserDetailLoginModel()?.status == true){
                val intent = Intent(this@SplashActivity, NewsActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        })


    }





    private fun intializeMembers() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.splash_main)
        mSplashViewModel = ViewModelProvider(this).get(SplashViewModel::class.java)
        mSessionManager = SessionManager.getInstance(application)
        mCommonUtils = CommonUtils()



    }







}