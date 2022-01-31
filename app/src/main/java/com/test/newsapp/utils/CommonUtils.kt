package com.test.newsapp.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.kaopiz.kprogresshud.KProgressHUD
import com.tapadoo.alerter.Alerter
import com.test.newsapp.R


class CommonUtils {

    private lateinit var kProgressHUD : KProgressHUD
    private lateinit var mSessionManager: SessionManager
    private lateinit var mCommonUtils: CommonUtils


    fun LogPrint(tag: String?, message: String?) {
        if (message != null) {
            Log.d(tag, message)
        }

    }

    fun ToastPrint(context: Context?, message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }




    interface APIURL {

        companion object {
            const val NEWS_LIST: String ="getNewsByCategoryId"
            const val BASE_URL: String = "http://198.12.156.249:45001/gist/api/gist/"

        }
    }

    interface ProgressDialog {
        companion object {
            const val showDialog = 10
            const val dismissDialog = 11
        }
    }

    fun showAlert(
        setDuration: Long,
        title: String?,
        text: String?,
        drawable: Int,
        activity: Activity,
    ) {
        Alerter.create(activity)
            .setTitle(title)
            .setText(text)
            .setDuration(setDuration)
            .setIcon(drawable)
            .setBackgroundColorRes(R.color.appcolor)
            .show()
    }

    fun showProgress(message: String?, context: Context) {
        mSessionManager = SessionManager.getInstance(context)!!
        mCommonUtils = CommonUtils()


        kProgressHUD = KProgressHUD.create(context)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setLabel(context.resources.getString(R.string.pleasewait))
            .setCancellable(false)
            .setAnimationSpeed(2)
            .setDimAmount(0.5f)
            .setBackgroundColor(R.color.appcolor)
            .show()
    }

    fun dismissProgress() {
        if (kProgressHUD != null && kProgressHUD.isShowing()) kProgressHUD.dismiss()
    }

    fun hideKeyboard(activity: Context) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }
















}