package com.huanchengfly.tieba.post.ui.utils

import com.huanchengfly.tieba.post.api.models.protos.Post
import com.huanchengfly.tieba.post.api.models.protos.ThreadInfo
import com.huanchengfly.tieba.post.models.protos.LoadPicPageData
import com.huanchengfly.tieba.post.models.protos.PhotoViewData
import com.huanchengfly.tieba.post.models.protos.PicItem
import com.huanchengfly.tieba.post.utils.ImageUtil

fun getPhotoViewData(
    post: Post,
    picId: String,
    picUrl: String,
    originUrl: String,
    showOriginBtn: Boolean,
    originSize: Int
): PhotoViewData? {
    if (post.from_forum == null) return null
    return PhotoViewData(
        data_ = LoadPicPageData(
            forumId = post.from_forum.id,
            forumName = post.from_forum.name,
            threadId = post.tid,
            postId = post.id,
            objType = "pb",
            picId = picId,
            picIndex = 1
        ),
        picItems = listOf(
            PicItem(
                picId = picId,
                picIndex = 1,
                url = picUrl,
                originUrl = originUrl,
                showOriginBtn = showOriginBtn,
                originSize = originSize
            )
        )
    )
}

fun getPhotoViewData(
    threadInfo: ThreadInfo,
    index: Int
): PhotoViewData {
    val media = threadInfo.media[index]
    return PhotoViewData(
        data_ = LoadPicPageData(
            forumId = threadInfo.forumId,
            forumName = threadInfo.forumName,
            threadId = threadInfo.threadId,
            postId = media.postId,
            seeLz = false,
            objType = "index",
            picId = ImageUtil.getPicId(media.originPic),
            picIndex = index + 1
        ),
        picItems = threadInfo.media.mapIndexed { mediaIndex, mediaItem ->
            PicItem(
                picId = ImageUtil.getPicId(mediaItem.originPic),
                picIndex = mediaIndex + 1,
                url = mediaItem.bigPic,
                originUrl = mediaItem.originPic,
                showOriginBtn = mediaItem.showOriginalBtn == 1,
                originSize = mediaItem.originSize
            )
        },
        index = index
    )
}