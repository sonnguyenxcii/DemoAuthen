package vn.nsn.app.ocb.api.dto

import com.google.gson.annotations.SerializedName

data class ChapterBalloonContentDTO(
        @SerializedName("full_image_url")
        val full_image_url: String?,
        @SerializedName("size")
        val size: String?
)