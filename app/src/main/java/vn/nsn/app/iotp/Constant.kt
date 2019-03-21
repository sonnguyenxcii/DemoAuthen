package vn.nsn.app.iotp

object Constant {
    const val HEADER_PLATFORM_VERSION = "X-Peep-iOS-Version"
    const val HEADER_SIGNATURE = "X-HMAC-SHA2"
    const val HEADER_AUTHORIZATION = "Authorization"
    const val AUTHORIZATION_PREFIX = "Token "
    const val HMAC_SECRET_KEY: String = "3c61df0f5ca23bc1f0876a2d556a04f96135b9c5b3e9c3e87bcd40078b8b5be4dadaa5c4a7214363181c8db7a81ff885fd0a231e212e336d7e95d615a8ae58d5"
    const val BLUR_VALUE = 5f
    const val PASSWORD_LENGTH = 6

    object Key {
        const val KEY_SUB_MENU = "sub_menu"
        const val KEY_LOG_TYPE = "log_type"
        const val KEY_EXTRA_DATA = "data"
        const val KEY_URL = "url"
        const val KEY_TITLE = "title"
    }

    enum class LayoutType {
        DOUBLE,
        TRIPLE,
        LARGE_LEFT,
        LARGE_RIGHT
    }

    object LogType {
        const val TYPE_HISTORY = 0
        const val TYPE_FAVOURITE = 1
    }

    object BadgeType {
        const val UPDATE = "update"
        const val SERIES = "series"
        const val NUM_CHAPTERS = "num_chapters"
        const val WDAY = "wday"
    }

    object GenderType {
        const val MALE = "male"
        const val FEMALE = "female"
        const val OTHER = "other"
    }

    object Gender {
        const val MALE = 1
        const val FEMALE = 0
    }

    object Story {
        const val TYPE_SERIES = "series"
        const val TYPE_ONE_SHOT = "one-shot"
        const val CONTENT_TYPE_TEXT = "text"
        const val CONTENT_TYPE_IMAGE = "image"
        const val CONTENT_TYPE_VIDEO = "video"
        const val ANIMATION_TYPE_TYPING = "typing"
        const val SHARE_MESSAGE = " - チャット小説アプリ『peep』"
        const val SHARE_TAG = "#peep"
    }

    object StoreMode {
        const val ACCOUNT = 0
        const val EPISODE = 1
        const val SUBSCRIPTION = 2
        const val HOME = 3
    }

    object Peep {
        const val INFINITY = -1

    }

    object ApiConst {
        const val PAGE_DEFAULT = "1"
        const val ITEM_PER_PAGE_DEFAULT = "20"
    }

    object Carousel {
        const val TYPE_STORY = "story"
        const val TYPE_NOTIFICATION = "notification"
    }

    object LoginBonus {
        const val DAY_1 = "100"
        const val DAY_5 = "250"
        const val DAY_10 = "300"
        const val DAY_OTHERS = "50"
    }

    object Twitter {
        const val CONSUMER_KEY = "0Jl0iLs9foG1FRFXZ81lgBUKW"
        const val CONSUMER_SECRET = "6oanAJ1k1kmRFPUQrcrUbDQbkvsEPpZU7CuZF05NPbllURZspm"
    }
}