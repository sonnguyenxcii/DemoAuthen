package vn.nsn.app.ocb.api.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class StoryDTO(
        @SerializedName("id")
        val id: Int?,
        @SerializedName("title")
        val title: String?,
        @SerializedName("description")
        val description: String?,
        @SerializedName("cover_url")
        val coverUrl: String?,
        @SerializedName("author_name")
        val authorName: String?,
        @SerializedName("num_chapters")
        val numChapters: Int?,
        @SerializedName("num_balloons")
        val numBalloons: Int?,
        @SerializedName("sum_consumed_peeps")
        val sumConsumedPeeps: Int?,
        @SerializedName("badge_strings")
        val badge: BadgeDTO?,
        @SerializedName("is_updated_string")
        val isUpdatedString: String?,
        @SerializedName("is_finished_string")
        val isFinishedString: String?,
        @SerializedName("update_type")
        val updateType: String?,
        @SerializedName("update_wday_string")
        val updateWdayString: String?,
        @SerializedName("genre")
        val genre: String?,
        @SerializedName("did_favorite")
        val didFavorite: Boolean?,
        @SerializedName("marker")
        val marker: StoryMarkerDTO?,
        @SerializedName("share_url")
        val shareUrl: String?,
        @SerializedName("video_page_url")
        val videoPageUrl: String?
): Serializable