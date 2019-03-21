package vn.nsn.app.ocb.api.dto

import com.google.gson.annotations.SerializedName

data class StoryCollectionsResponse(
        @SerializedName("collections")
        val collections: List<StoryCollectionDTO>
)