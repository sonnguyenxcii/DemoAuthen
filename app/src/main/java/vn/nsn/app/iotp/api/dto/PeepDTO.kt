package vn.nsn.app.iotp.api.dto

import com.google.gson.annotations.SerializedName

data class PeepDTO(
        @SerializedName("paid")
        val paid: Int?,
        @SerializedName("free")
        val free: Int?,
        @SerializedName("recover_at")
        val recoverAt: String?
)