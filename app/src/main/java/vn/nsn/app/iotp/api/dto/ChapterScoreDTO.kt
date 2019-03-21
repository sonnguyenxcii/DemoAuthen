package vn.nsn.app.iotp.api.dto

import com.google.gson.annotations.SerializedName

data class ChapterScoreDTO(
        @SerializedName("body")
        val body: Int?
)