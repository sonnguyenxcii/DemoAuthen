package vn.nsn.app.ocb.api.dto

import com.google.gson.annotations.SerializedName

data class LoginDTO(
        @SerializedName("login")
        val login: EmailAndPassDTO?
)