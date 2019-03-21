package vn.nsn.app.ocb.api.dto

import com.google.gson.annotations.SerializedName

class UserProgressDTO(
        @SerializedName("progresses")
        val progresses: HashMap<String, StoryProgressDTO>?
)