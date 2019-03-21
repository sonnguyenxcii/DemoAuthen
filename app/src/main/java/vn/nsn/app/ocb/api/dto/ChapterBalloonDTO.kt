package vn.nsn.app.ocb.api.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ChapterBalloonDTO(
        @SerializedName("character")
        val character: StoryCharacterDTO?,
        @SerializedName("body")
        val body: String?,
        @SerializedName("background_image_id")
        val backgroundImageId: Int?,
        @SerializedName("position")
        val position: String?,
        @SerializedName("content_type")
        val content_type: String?,
        @SerializedName("animation_type")
        val animation_type: String?,
        @SerializedName("content")
        val content: ChapterBalloonContentDTO?
) : Serializable