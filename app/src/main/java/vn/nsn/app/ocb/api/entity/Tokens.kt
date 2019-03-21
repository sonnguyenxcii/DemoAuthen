package vn.nsn.app.ocb.api.entity

data class Tokens(
        val accessToken: String? = null,
        val refreshToken: String? = null,
        val tokenExpiresAt: String = ""
)