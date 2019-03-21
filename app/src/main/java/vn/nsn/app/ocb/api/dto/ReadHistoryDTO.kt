package vn.nsn.app.ocb.api.dto

import com.google.gson.annotations.SerializedName

class ReadHistoryDTO {
    @SerializedName("stories")
    var readHistories: ArrayList<StoryDTO>? = null
}