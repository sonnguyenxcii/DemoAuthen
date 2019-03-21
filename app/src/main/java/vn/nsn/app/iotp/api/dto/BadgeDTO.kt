package vn.nsn.app.iotp.api.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BadgeDTO(
        @SerializedName("update")
        val update: String?,
        @SerializedName("series")
        val series: String?,
        @SerializedName("num_chapters")
        val numChapters: String?,
        @SerializedName("wday")
        val wday: String?
): Serializable