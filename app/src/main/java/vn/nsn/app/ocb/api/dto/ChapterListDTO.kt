package vn.nsn.app.ocb.api.dto

import com.google.gson.annotations.SerializedName

class ChapterListDTO (
    @SerializedName("chapters")
    val chapters: List<ChaptersListItemDTO>
)