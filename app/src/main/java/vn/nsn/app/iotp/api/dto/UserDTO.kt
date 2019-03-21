package vn.nsn.app.iotp.api.dto

import com.google.gson.annotations.SerializedName

data class UserDTO(
        @SerializedName("id")
        val id: Int?,
        @SerializedName("did_get_profile_fill_bonus")
        val didGetProfileFillBonus: Boolean?,
        @SerializedName("did_get_invite_bonus")
        val didGetInviteBonus: Boolean?,
        @SerializedName("did_get_video_bonus")
        val didGetVideoBonus: Boolean?,
        @SerializedName("did_get_share_bonus")
        val didGetShareBonus: Boolean?,
        @SerializedName("did_get_free_subscription")
        val didGetFreeSubscription: Boolean?,
        @SerializedName("subscription_type")
        val subscriptionType: String?,
        @SerializedName("is_in_subscription")
        val isInSubscription: Boolean?,
        @SerializedName("continuous_logins")
        val continuousLogins: Int?,
        @SerializedName("can_get_login_bonus")
        val canGetLoginBonus: Boolean?,
        @SerializedName("email")
        val email: String?,
        @SerializedName("profile")
        val profile: ProfileDTO?,
        @SerializedName("peep")
        val peep: PeepDTO?,
        @SerializedName("tokens")
        val tokens: TokensDTO?
)