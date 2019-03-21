package vn.nsn.app.iotp.api.dto

import com.google.gson.annotations.SerializedName

data class ImageDTO(
        @SerializedName("id")
        val id: Int?,
        @SerializedName("url")
        val url: String?
)