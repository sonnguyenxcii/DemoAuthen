package vn.nsn.app.ocb.api.dto

import com.google.gson.annotations.SerializedName

data class ReadingChapterDetailDTO (
        @SerializedName("chapter")
        val chapter: DetailedChapterDTO
)