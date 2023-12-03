import com.tripbook.libs.network.model.response.ArticleDetailResponse
import com.tripbook.tripbook.domain.model.ArticleDetail
import com.tripbook.tripbook.domain.model.MemberSimple
import com.tripbook.libs.network.model.response.ImageResponse
import com.tripbook.tripbook.domain.model.Comment
import com.tripbook.libs.network.model.response.CommentResponse
import com.tripbook.tripbook.domain.model.Image


fun ArticleDetailResponse.toArticleDetail() = ArticleDetail(
    id = id,
    title = title,
    content = content,
    author = MemberSimple(
        id = author.id,
        name = author.name,
        profileUrl = author.profileUrl ?: "",
        role = author.role
    ),
    imageList = imageList?.map { it.toImage() },
    thumbnail = thumbnail?.toImage(),
    tagList = tagList,
    numberOfHearts = numberOfHearts,
    numberOfBookmarks = numberOfBookmarks,
    commentList = commentList.map { it.toComment() },
    createdAt = createdAt,
    updatedAt = updatedAt,
    heart = heart,
    bookmark = bookmark
)

fun ImageResponse.toImage() = Image(
    id = id,
    url = url
)

fun CommentResponse.toComment() = Comment(
    id = id,
    content = content,
    author = MemberSimple(
        id = author.id,
        name = author.name,
        profileUrl = author.profileUrl ?: "",
        role = author.role
    ),
    //childList = childList.map { it.toComment() },
    childList = childList, //여기 코멘트 추가해야 됨
    createdAt = createdAt,
    updatedAt = updatedAt
)

