package vn.nsn.app.iotp.api.dto

import com.google.gson.annotations.SerializedName

data class LoginDTO(
        @SerializedName("login")
        val login: EmailAndPassDTO?
)