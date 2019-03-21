package vn.nsn.app.ocb.api.dto

import com.google.gson.annotations.SerializedName

class ChaptersListItemDTO (
    @SerializedName("id")
    val chapterId: Int?,
    @SerializedName("number")
    val chapterNumber: String?,
    @SerializedName("title")
    val title: String,
    @SerializedName("sum_consumed_peeps")
    val sumComsumedPeeps: String?,
    @SerializedName("did_complete")
    val didComplete: Boolean,
    @SerializedName("created_at")
    val createdAt: String
)