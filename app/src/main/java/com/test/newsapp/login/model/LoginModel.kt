package com.test.newsapp.login.model

import com.google.gson.annotations.SerializedName


data class LoginModel ( @SerializedName("name")
                        var name: String? ,

    @SerializedName("password")
    var password: String?,

    @SerializedName("status") var status : Boolean
)