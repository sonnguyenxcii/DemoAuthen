package vn.nsn.app.ocb.api.dto

import com.google.gson.annotations.SerializedName

data class ChapterScoreDTO(
        @SerializedName("body")
        val body: Int?
)