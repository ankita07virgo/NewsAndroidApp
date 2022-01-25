package com.test.newsapp.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.test.newsapp.login.model.LoginModel
import java.lang.reflect.Type


class SessionManager {


    fun setUserLoginDetailModel(loginDetailModel: LoginModel) {
        val edit: SharedPreferences.Editor? = sharedPreferences?.edit()
        val gson = Gson()
        val paidObject = gson.toJson(loginDetailModel)
        edit?.putString(TAG_USERDETAILMODLE_PREF, paidObject)
        edit?.commit()

    }

    fun getUserDetailLoginModel(): LoginModel? {


        val userObjectString: String? = sharedPreferences?.getString(
            TAG_USERDETAILMODLE_PREF, ""
        )
        val gson = Gson()

        val loginModel: LoginModel? = gson.fromJson(userObjectString, LoginModel::class.java)
        return loginModel
    }



    companion object {
        const val PREF_NAME = "newsApp"
        const val TAG_USERDETAILMODLE_PREF = "UserLoginDetail"

        private var sessionManager: SessionManager? = null
        private var sharedPreferences: SharedPreferences? = null
        private var context: Context? = null
        private var editor: SharedPreferences.Editor? = null
        fun getInstance(context1: Context?): SessionManager? {
            context = context1
            if (sessionManager == null) {
                sessionManager = SessionManager()
                sharedPreferences = context?.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

            }
            return sessionManager
        }
    }

    fun clearpreference(tag: String?, mContext: Context) {
         var editor: SharedPreferences.Editor? = null
        var sharedPreferences: SharedPreferences? = null
        sharedPreferences = mContext.getSharedPreferences(
            PREF_NAME, Context.MODE_PRIVATE
        )
        editor = sharedPreferences.edit()
        editor.putString(tag, "")
        editor.remove(tag).apply()
    }
}