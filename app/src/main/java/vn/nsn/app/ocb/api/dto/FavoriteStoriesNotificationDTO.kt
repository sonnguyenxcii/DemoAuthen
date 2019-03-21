package vn.nsn.app.ocb.api.dto

import com.google.gson.annotations.SerializedName

data class FavoriteStoriesNotificationDTO(
        @SerializedName("will_notify_update")
        val notification: Boolean?
)