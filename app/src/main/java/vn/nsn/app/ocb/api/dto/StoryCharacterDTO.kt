package vn.nsn.app.ocb.api.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class StoryCharacterDTO(
        @SerializedName("id")
        val id: Int?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("color")
        val color: String?
): Serializable