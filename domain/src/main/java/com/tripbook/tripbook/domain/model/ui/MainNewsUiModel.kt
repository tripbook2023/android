package com.tripbook.tripbook.domain.model.ui

import com.tripbook.tripbook.domain.model.Banner
import com.tripbook.tripbook.domain.model.News

//data class MainNewsUiModel(
//    val banners: List<Banner>,
//    val cards: List<News>
//)

sealed class MainNewsUiModel(val viewType: Int) {
    data class Banners(val banner: List<Banner>): MainNewsUiModel(0)

    data class Card(val news: News): MainNewsUiModel(2)

    data class MyCards(val news: List<News>): MainNewsUiModel(1)
}
