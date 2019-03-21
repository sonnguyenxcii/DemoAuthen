package vn.nsn.app.iotp.api.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ChapterReactionDTO(
        @SerializedName("love")
        val love: Int?,
        @SerializedName("laugh")
        val laugh: Int?,
        @SerializedName("fear")
        val fear: Int?,
        @SerializedName("sad")
        val sad: Int?,
        @SerializedName("anger")
        val anger: Int?
): Serializable