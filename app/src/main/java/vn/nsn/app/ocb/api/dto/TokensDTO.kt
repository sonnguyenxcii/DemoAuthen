package vn.nsn.app.ocb.api.dto

import com.google.gson.annotations.SerializedName

data class TokensDTO(
        @SerializedName("access_token")
        val accessToken: String?,
        @SerializedName("refresh_token")
        val refreshToken: String?,
        @SerializedName("token_expires_at")
        val tokenExpiresAt: String?
)