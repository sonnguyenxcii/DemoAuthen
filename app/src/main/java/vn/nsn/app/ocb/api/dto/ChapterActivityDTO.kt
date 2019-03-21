package vn.nsn.app.ocb.api.dto

import com.google.gson.annotations.SerializedName

data class ChapterActivityDTO(
        @SerializedName("num_opened_balloons")
        val numOpenedBalloons: Int?,
        @SerializedName("reaction")
        val reaction: String?
)