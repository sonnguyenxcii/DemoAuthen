package vn.nsn.app.iotp.api.dto

import com.google.gson.annotations.SerializedName

data class SubMenuDTO(
        @SerializedName("title")
        val title: String?,
        @SerializedName("badges")
        val badges: List<String>?,
        @SerializedName("key")
        val key: String?,
        @SerializedName("show_rank")
        val showRank: Boolean?,
        @SerializedName("show_progress")
        val showProgress: Boolean?
)