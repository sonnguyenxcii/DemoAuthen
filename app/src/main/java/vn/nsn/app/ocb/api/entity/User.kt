package vn.nsn.app.ocb.api.entity

data class User(
        val id: Int,
        val didGetProfileFillBonus: Boolean,
        val didGetInviteBonus: Boolean,
        val didGetVideoBonus: Boolean,
        val didGetShareBonus: Boolean,
        val didGetFreeSubscription: Boolean,
        val subscriptionType: String,
        val isInSubscription: Boolean,
        val continuousLogins: Int,
        val canGetLoginBonus: Boolean,
        val email: String,
        val profile: Profile,
        val peep: Peep,
        val tokens: Tokens
)