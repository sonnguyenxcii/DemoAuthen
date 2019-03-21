package vn.nsn.app.ocb.api.dto

import com.google.gson.annotations.SerializedName

class NotificationDTO(
        @SerializedName("id")
        val id: Int?,
        @SerializedName("title")
        val title: String?,
        @SerializedName("url")
        val url: String?,
        @SerializedName("background_image_url")
        val backgroundImageUrl: String?
)