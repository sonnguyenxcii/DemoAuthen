package vn.nsn.app.ocb.api.entity

data class Profile(
        val name: String = "",
        val gender: String = "",
        val age: Int = -1,
        val iconUrl: String = ""
)