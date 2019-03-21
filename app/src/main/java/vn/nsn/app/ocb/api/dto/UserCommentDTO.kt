package vn.nsn.app.ocb.api.dto

import com.google.gson.annotations.SerializedName

data class UserCommentDTO(
        @SerializedName("name")
        val name: String?,
        @SerializedName("icon_url")
        val iconUrl: String?,
        @SerializedName("is_myself")
        val isMyself: Boolean?
)