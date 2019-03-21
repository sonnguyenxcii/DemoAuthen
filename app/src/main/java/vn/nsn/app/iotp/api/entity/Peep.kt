package vn.nsn.app.iotp.api.entity

data class Peep(
        val paid: Int = 0,
        val free: Int = 0,
        val recoverAt: String = ""
)