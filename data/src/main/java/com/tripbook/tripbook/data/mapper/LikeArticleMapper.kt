
import com.tripbook.libs.network.model.response.LikeArticleResponse
import com.tripbook.tripbook.domain.model.LikeArticle


fun LikeArticleResponse.toLikeArticle() = LikeArticle(
    id = id,
    heartNum = heartNum,
    bookmarkNum = bookmarkNum,
    heart = heart,
    bookmark = bookmark
)

