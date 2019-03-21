package vn.nsn.app.iotp.api.dto

import com.google.gson.annotations.SerializedName

class GenreDTO(
        @SerializedName("title")
        val title: String?,
        @SerializedName("stories")
        val stories: ArrayList<StoryDTO>?
)