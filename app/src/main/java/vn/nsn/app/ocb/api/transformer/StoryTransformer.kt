package vn.nsn.app.ocb.api.transformer

import vn.nsn.app.ocb.api.dto.*
import vn.nsn.app.ocb.api.entity.*

fun SubMenuDTO.toSubMenu() = SubMenu(
        title ?: "",
        badges ?: listOf(),
        key ?: "",
        showRank ?: false,
        showProgress ?: false
)

fun BadgeDTO.toBadge() = Badge(
        update ?: "",
        series ?: "",
        numChapters ?: "",
        wday ?: ""
)

fun StoryMarkerDTO.toStoryMarker() = StoryMarker(
        chapterId ?: -1,
        position ?: -1
)

fun StoryDTO.toStory() = Story(
        id ?: -1,
        title ?: "",
        description ?: "",
        coverUrl ?: "",
        authorName ?: "",
        numChapters ?: 0,
        numBalloons ?: 0,
        sumConsumedPeeps ?: 0,
        badge?.toBadge() ?: Badge("", "", "", ""),
        isUpdatedString ?: "",
        isFinishedString ?: "",
        updateType ?: "",
        updateWdayString ?: "",
        genre ?: "",
        didFavorite ?: false,
        marker?.toStoryMarker() ?: StoryMarker(-1, -1),
        shareUrl ?: "",
        videoPageUrl ?: "",
        0
)

fun StoryCollectionDTO.toStoryCollection() = StoryCollection(
        layout ?: "",
        stories?.map {
            it.toStory()
        } ?: listOf()
)

fun ArrayList<StoryDTO>.toStories(): ArrayList<Story> {
    return ArrayList(map {
        it.toStory()
    })
}

fun ImageDTO.toImage() = Image(
        id ?: -1,
        url ?: ""
)

fun ChapterReactionDTO.toChapterReaction() = ChapterReaction(
        love ?: -1,
        laugh ?: -1,
        fear ?: -1,
        sad ?: -1,
        anger ?: -1

)

fun ChapterBalloonDTO.toChapterBalloon() = ChapterBalloon(
        character?.toStoryCharacter() ?: StoryCharacter(-1, "", ""),
        body ?: "",
        backgroundImageId ?: -1,
        position ?: "",
        content_type ?: "",
        animation_type ?: "",
        content?.toChapterBalloonContent() ?: ChapterBalloonContent("", "")
)

fun ChapterBalloonContentDTO.toChapterBalloonContent() = ChapterBalloonContent(
        full_image_url ?: "",
        size ?: ""
)

fun StoryCharacterDTO.toStoryCharacter() = StoryCharacter(
        id ?: -1,
        name ?: "",
        color ?: ""

)

fun DetailedChapterDTO.toDetailChapter() = DetailedChapter(
        id ?: -1,
        number ?: -1,
        title ?: "",
        numComments ?: -1,
        backgroundImages?.map { it.toImage() } ?: listOf(),
        reactedWith ?: "",
        reactions?.toChapterReaction() ?: ChapterReaction(-1, -1, -1, -1, -1),
        numOpenedBalloons ?: -1,
        balloons?.map { it.toChapterBalloon() } ?: listOf(),
        previousChapterId ?: -1,
        nextChapterId ?: -1
)


fun TutorialStoryDTO.toTutorialStory() = TutorialStory(

        chapter?.toDetailChapter() ?: DetailedChapter(-1, -1, "",
                -1, listOf(), "", ChapterReaction(-1, -1,
                -1, -1, -1), -1,
                listOf(), -1, -1),

        story?.toStory() ?: Story(-1, "", "", "", "", -1, -1,
                -1, Badge("", "", "", ""), ""
                , "", "", "", "", false, StoryMarker(-1, -1)
                , "", "", -1)

)

fun StoryProgressDTO.toStoryProgress(): StoryProgress {
    return StoryProgress(numberOpenedBalloons
            ?: HashMap(), sumBalloons ?: 0)
}

