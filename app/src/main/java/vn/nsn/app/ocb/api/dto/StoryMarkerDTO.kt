package vn.nsn.app.ocb.api.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class StoryMarkerDTO(
        @SerializedName("chapter_id")
        val chapterId: Int?,
        @SerializedName("position")
        val position: Int?
): Serializable