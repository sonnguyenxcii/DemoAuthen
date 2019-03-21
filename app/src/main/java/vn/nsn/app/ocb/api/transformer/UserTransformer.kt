package vn.nsn.app.ocb.api.transformer

import vn.nsn.app.ocb.api.dto.*
import vn.nsn.app.ocb.api.entity.*
import vn.nsn.app.ocb.extension.toHashMap

fun UserDTO.toUser() = User(
        id ?: -1,
        didGetProfileFillBonus ?: false,
        didGetInviteBonus ?: false,
        didGetVideoBonus ?: false,
        didGetShareBonus ?: false,
        didGetFreeSubscription ?: false,
        subscriptionType ?: "",
        isInSubscription ?: false,
        continuousLogins ?: -1,
        canGetLoginBonus ?: false,
        email ?: "",
        profile?.toProfile() ?: Profile(),
        peep?.toPeep() ?: Peep(),
        tokens?.toTokens() ?: Tokens()
)

fun User.toUserDTO() = UserDTO(
        id,
        didGetProfileFillBonus,
        didGetInviteBonus,
        didGetVideoBonus,
        didGetShareBonus,
        didGetFreeSubscription,
        subscriptionType,
        isInSubscription,
        continuousLogins,
        canGetLoginBonus,
        email,
        profile.toProfileDTO(),
        peep.toPeepDTO(),
        tokens.toTokensDTO()
)

fun ProfileDTO.toProfile() = Profile(
        name ?: "",
        gender ?: "",
        age ?: -1,
        iconUrl ?: ""
)

fun Profile.toProfileDTO() = ProfileDTO(
        name,
        gender,
        age,
        iconUrl
)

fun PeepDTO.toPeep() = Peep(
        paid ?: 0,
        free ?: 0,
        recoverAt ?: ""
)

fun Peep.toPeepDTO() = PeepDTO(
        paid,
        free,
        recoverAt
)

fun TokensDTO.toTokens() = Tokens(
        accessToken,
        refreshToken,
        tokenExpiresAt ?: ""
)

fun Tokens.toTokensDTO() = TokensDTO(
        accessToken,
        refreshToken,
        tokenExpiresAt
)

fun UserProgressDTO.toUserProgress(): UserProgress {
    return UserProgress(
            progresses?.mapValues { it.value.toStoryProgress() }?.toHashMap() ?: HashMap()
    )
}

fun UserCodeDTO.toUserCode(): UserCode {
    var userCode = ""
    code?.forEach { (_, value) ->
        userCode = value ?: ""
    }

    return UserCode(userCode)
}