package vn.nsn.app.ocb.api.dto

import com.google.gson.annotations.SerializedName

class StoryActivityDTO(
        @SerializedName("id")
        val id: Int?,
        @SerializedName("chapters")
        val chapters: List<Map<String, ChapterActivityDTO>>?
)