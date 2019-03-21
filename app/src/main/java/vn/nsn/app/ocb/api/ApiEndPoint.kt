package vn.nsn.app.ocb.api

import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import vn.nsn.app.ocb.BuildConfig
import vn.nsn.app.ocb.Constant
import vn.nsn.app.ocb.api.dto.*

interface ApiEndPoint {

    companion object {
        // Parameters
        private const val PAGE = "page"
        private const val PER = "per"
    }

    @POST("registrations")
    fun createUser(@Header(Constant.HEADER_PLATFORM_VERSION) version: String? = BuildConfig.VERSION_NAME): Single<UserResponse>

    @PUT("registrations")
    fun updateUser(@Header(Constant.HEADER_PLATFORM_VERSION) version: String? = BuildConfig.VERSION_NAME, @Header(Constant.HEADER_AUTHORIZATION) token: String?, @Body login: LoginDTO): Single<LoginResponseDTO>

    @PUT("sessions")
    fun refreshToken(@Header(Constant.HEADER_PLATFORM_VERSION) version: String? = BuildConfig.VERSION_NAME, @Body refreshToken: RefreshTokenDTO): Call<UserResponse>

    @GET("me")
    fun getCurrentUser(@Header(Constant.HEADER_PLATFORM_VERSION) version: String? = BuildConfig.VERSION_NAME, @Header(Constant.HEADER_AUTHORIZATION) token: String?): Single<UserResponse>

    @GET("submenus")
    fun getSubMenus(@Header(Constant.HEADER_PLATFORM_VERSION) version: String? = BuildConfig.VERSION_NAME, @Header(Constant.HEADER_AUTHORIZATION) token: String?): Single<SubMenusResponse>

    @GET
    fun getStoriesByGenre(@Header(Constant.HEADER_PLATFORM_VERSION) version: String? = BuildConfig.VERSION_NAME, @Header(Constant.HEADER_AUTHORIZATION) token: String?, @Url path: String?): Single<StoryCollectionsResponse>

    @GET("featured_stories")
    fun getFeaturedStories(@Header(Constant.HEADER_PLATFORM_VERSION) version: String? = BuildConfig.VERSION_NAME, @Header(Constant.HEADER_AUTHORIZATION) token: String?)

    @GET("me/read_histories")
    fun getHistoryStories(@Header(Constant.HEADER_PLATFORM_VERSION) version: String? = BuildConfig.VERSION_NAME,
                          @Header(Constant.HEADER_AUTHORIZATION) token: String?,
                          @Query(PAGE) page: String = Constant.ApiConst.PAGE_DEFAULT,
                          @Query(PER) per: String = Constant.ApiConst.ITEM_PER_PAGE_DEFAULT
    ): Single<StoryCollectionDTO>

    @GET("me/favorite_stories")
    fun getFavouriteStories(@Header(Constant.HEADER_PLATFORM_VERSION) version: String? = BuildConfig.VERSION_NAME,
                            @Header(Constant.HEADER_AUTHORIZATION) token: String?,
                            @Query(PAGE) page: String = Constant.ApiConst.PAGE_DEFAULT,
                            @Query(PER) per: String = Constant.ApiConst.ITEM_PER_PAGE_DEFAULT
    ): Single<StoryCollectionDTO>

    @GET("me/read_histories")
    fun getReadStories(
            @Header(Constant.HEADER_PLATFORM_VERSION) version: String? = BuildConfig.VERSION_NAME,
            @Header(Constant.HEADER_AUTHORIZATION) token: String?,
            @Query(PAGE) page: String = Constant.ApiConst.PAGE_DEFAULT,
            @Query(PER) per: String = Constant.ApiConst.ITEM_PER_PAGE_DEFAULT
    ): Single<ReadHistoryDTO>

    @GET("headlines")
    fun getHeadlines(
            @Header(Constant.HEADER_PLATFORM_VERSION) version: String? = BuildConfig.VERSION_NAME,
            @Header(Constant.HEADER_AUTHORIZATION) token: String?
    ): Single<HeadlineDTO>

    @GET("me/progresses")
    fun getUserProgresses(
            @Header(Constant.HEADER_PLATFORM_VERSION) version: String? = BuildConfig.VERSION_NAME,
            @Header(Constant.HEADER_AUTHORIZATION) token: String?
    ): Single<UserProgressDTO>

    @GET("tutorial_story")
    fun getTutorialStory(@Header(Constant.HEADER_PLATFORM_VERSION) version: String? = BuildConfig.VERSION_NAME): Single<TutorialStoryDTO>

    @PUT("me/device_token")
    fun updateDeviceToken(@Header(Constant.HEADER_PLATFORM_VERSION) version: String? = BuildConfig.VERSION_NAME,
                          @Header(Constant.HEADER_AUTHORIZATION) token: String?,
                          @Body deviceToken: UpdateDeviceTokenDTO): Single<Any>

    @PATCH("me/push_settings")
    fun updatePushSetting(@Header(Constant.HEADER_PLATFORM_VERSION) version: String? = BuildConfig.VERSION_NAME,
                          @Header(Constant.HEADER_AUTHORIZATION) token: String?,
                          @Body settings: SettingPushDTO): Single<SettingPushDTO>

    @GET("me/push_settings")
    fun getPushSetting(@Header(Constant.HEADER_PLATFORM_VERSION) version: String? = BuildConfig.VERSION_NAME,
                       @Header(Constant.HEADER_AUTHORIZATION) token: String?): Single<SettingPushDTO>

    @Multipart
    @PATCH("me/profile")
    fun updateProfile(@Header(Constant.HEADER_PLATFORM_VERSION) version: String? = BuildConfig.VERSION_NAME,
                      @Header(Constant.HEADER_AUTHORIZATION) token: String?,
                      @Part("profile[name]") name: RequestBody? = null,
                      @Part("profile[gender]") gender: RequestBody? = null,
                      @Part("profile[age]") age: RequestBody? = null,
                      @Part icon: MultipartBody.Part? = null
    ): Single<UserDTO>

    @GET("me/invite_code")
    fun getInviteCode(@Header(Constant.HEADER_PLATFORM_VERSION) version: String? = BuildConfig.VERSION_NAME,
                      @Header(Constant.HEADER_AUTHORIZATION) token: String?): Single<CodeInviteDTO>

    @GET("store/products")
    fun getStoreProducts(@Header(Constant.HEADER_PLATFORM_VERSION) version: String? = BuildConfig.VERSION_NAME,
                         @Header(Constant.HEADER_AUTHORIZATION) token: String?): Single<StoreProductsResponse>

    @GET("me/purchase_histories")
    fun getPurchaseHistories(@Header(Constant.HEADER_PLATFORM_VERSION) version: String? = BuildConfig.VERSION_NAME,
                             @Header(Constant.HEADER_AUTHORIZATION) token: String?,
                             @Query(PAGE) page: String = Constant.ApiConst.PAGE_DEFAULT,
                             @Query(PER) per: String = Constant.ApiConst.ITEM_PER_PAGE_DEFAULT): Single<PurchaseHistoriesResponse>

    @GET("/stories/{storyID}/chapter")
    fun getChapters(@Header(Constant.HEADER_PLATFORM_VERSION) version: String? = BuildConfig.VERSION_NAME,
                    @Header(Constant.HEADER_AUTHORIZATION) token: String?,
                    @Path("storyID") storyID: Int): Single<ChapterListDTO>

    @PUT("stories/{story_id}/favorite")
    fun addFavoriteStories(@Header(Constant.HEADER_PLATFORM_VERSION) version: String? = BuildConfig.VERSION_NAME,
                           @Header(Constant.HEADER_AUTHORIZATION) token: String?,
                           @Path("story_id") storyId: Int,
                           @Body option: FavoriteStoriesNotificationDTO
    ): Single<StoryDTO>


    @DELETE("stories/{story_id}/favorite")
    fun removeFavoriteStories(@Header(Constant.HEADER_PLATFORM_VERSION) version: String? = BuildConfig.VERSION_NAME,
                              @Header(Constant.HEADER_AUTHORIZATION) token: String?,
                              @Path("story_id") storyId: Int
    ): Single<Any>


    @GET("stories/{story_id}")
    fun getStoryInfo(@Header(Constant.HEADER_PLATFORM_VERSION) version: String? = BuildConfig.VERSION_NAME,
                     @Header(Constant.HEADER_AUTHORIZATION) token: String?,
                     @Path("story_id") storyId: Int
    ): Single<CarouselDTO>


    @GET("chapters/{chapter_id}")
    fun getChapterReading(@Header(Constant.HEADER_PLATFORM_VERSION) version: String? = BuildConfig.VERSION_NAME,
                          @Header(Constant.HEADER_AUTHORIZATION) token: String?,
                          @Path("chapter_id") chapterId: Int
    ): Single<ReadingChapterDetailDTO>

    @POST("me/login")
    fun login(@Header(Constant.HEADER_PLATFORM_VERSION) version: String? = BuildConfig.VERSION_NAME,
              @Header(Constant.HEADER_AUTHORIZATION) token: String?): Single<UserDTO>

    @POST("/me/inviter")
    fun inputInvitationCode(@Header(Constant.HEADER_PLATFORM_VERSION) version: String? = BuildConfig.VERSION_NAME,
                            @Header(Constant.HEADER_AUTHORIZATION) token: String?,
                            @Body inviteCode: InputInvitationCodeDTO): Single<UserDTO>

    @GET("me/user_code")
    fun getUserCode(@Header(Constant.HEADER_PLATFORM_VERSION) version: String? = BuildConfig.VERSION_NAME,
                    @Header(Constant.HEADER_AUTHORIZATION) token: String?): Single<UserCodeDTO>


    @PUT("stories/{story_id}/marker")
    fun putStoryMarker(@Header(Constant.HEADER_PLATFORM_VERSION) version: String? = BuildConfig.VERSION_NAME,
                       @Header(Constant.HEADER_AUTHORIZATION) token: String?,
                       @Path("story_id") storyId: Int,
                       @Body marker: StoryMarkerDTO

    ): Single<UserResponse>

}