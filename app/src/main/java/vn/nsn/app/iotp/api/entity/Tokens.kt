package vn.nsn.app.iotp.api.entity

data class Tokens(
        val accessToken: String? = null,
        val refreshToken: String? = null,
        val tokenExpiresAt: String = ""
)