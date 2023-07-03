package com.tripbook.tripbook.domain.model.ui

import com.tripbook.tripbook.domain.model.Banner
import com.tripbook.tripbook.domain.model.News

//data class MainNewsUiModel(
//    val banners: List<Banner>,
//    val cards: List<News>
//)

sealed class MainNewsUiModel(val viewType: Int) {
    data class Banners(val list: List<Banner>): MainNewsUiModel(0)

    data class Card(val news: News): MainNewsUiModel(1)
}
