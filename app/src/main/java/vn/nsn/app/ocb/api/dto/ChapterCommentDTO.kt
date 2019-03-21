package vn.nsn.app.ocb.api.dto

import com.google.gson.annotations.SerializedName

data class ChapterCommentDTO(
        @SerializedName("id")
        val id: Int?,
        @SerializedName("user")
        val user: UserCommentDTO?,
        @SerializedName("body")
        val body: String?,
        @SerializedName("num_likes")
        val numLikes: Int?,
        @SerializedName("did_like")
        val didLike: Boolean?,
        @SerializedName("created_at")
        val createdAt: String?
)