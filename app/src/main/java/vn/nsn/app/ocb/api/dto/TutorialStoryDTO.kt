package vn.nsn.app.ocb.api.dto

import com.google.gson.annotations.SerializedName

data class TutorialStoryDTO(
        @SerializedName("chapter")
        val chapter: DetailedChapterDTO?,
        @SerializedName("story")
        val story: StoryDTO?
)