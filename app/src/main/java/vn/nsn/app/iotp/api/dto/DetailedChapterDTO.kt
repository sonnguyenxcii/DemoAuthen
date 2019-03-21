package vn.nsn.app.iotp.api.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DetailedChapterDTO(
        @SerializedName("id")
        val id: Int?,
        @SerializedName("number")
        val number: Int?,
        @SerializedName("title")
        val title: String?,
        @SerializedName("num_comments")
        val numComments: Int?,
        @SerializedName("background_images")
        val backgroundImages: List<ImageDTO>?,
        @SerializedName("reacted_with")
        val reactedWith: String?,
        @SerializedName("reactions")
        val reactions: ChapterReactionDTO?,
        @SerializedName("num_opened_balloons")
        val numOpenedBalloons: Int?,
        @SerializedName("balloons")
        val balloons: List<ChapterBalloonDTO>?,
        @SerializedName("previous_chapter_id")
        val previousChapterId: Int?,
        @SerializedName("next_chapter_id")
        val nextChapterId: Int?
) : Serializable