package vn.nsn.app.ocb.api.dto

import com.google.gson.annotations.SerializedName

data class StoryCollectionDTO(
        @SerializedName("layout")
        val layout: String?,
        @SerializedName("stories")
        val stories: List<StoryDTO>?
)