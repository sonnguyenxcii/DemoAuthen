package vn.nsn.app.iotp.api.dto

import com.google.gson.annotations.SerializedName

data class RefreshTokenDTO(
        @SerializedName("refresh_token")
        val refreshToken: String?
)