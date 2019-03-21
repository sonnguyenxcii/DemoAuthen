package vn.nsn.app.ocb.api.dto

import com.google.gson.annotations.SerializedName

data class PushSettingDTO(

        @SerializedName("admin")
        val admin: Boolean?,

        @SerializedName("peep_recovery")
        val peepRecovery: Boolean?,

        @SerializedName("story_updates")
        val storyUpdates: Boolean?

)