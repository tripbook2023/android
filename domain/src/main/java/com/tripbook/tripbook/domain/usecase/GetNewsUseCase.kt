package com.tripbook.tripbook.domain.usecase

import com.tripbook.tripbook.domain.model.Banner
import com.tripbook.tripbook.domain.model.News
import com.tripbook.tripbook.domain.model.NewsApplyStatus
import com.tripbook.tripbook.domain.model.ui.MainNewsUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    // TODO : 여기다가 Repository를 통한 서버와의 통신 필요
) {
    operator fun invoke(): Flow<List<MainNewsUiModel>> = combine(flow {
        emit(listOf(
            Banner("이벤트 타이틀 1", ""),
            Banner("이벤트 타이틀 2", ""),
            Banner("이벤트 타이틀 3", "")
        ))
    }, flow {
        emit(
            listOf(
                News(
                    "혼자가기 좋은 벚꽃여행지 BEST5",
                    "",
                    1000,
                    false,
                    1500,
                    applyStatus = NewsApplyStatus.APPLY_ONGOING,
                    ""
                ),
                News(
                    "업무가 하기 싫을 때 기분 전환할 수 있는 바다 여행지 BEST3",
                    "",
                    1000,
                    false,
                    1500,
                    applyStatus = NewsApplyStatus.APPLY_ONGOING,
                    ""
                ),
                News(
                    "혼자가기 좋은 벚꽃여행지 BEST5",
                    "",
                    1000,
                    false,
                    1500,
                    applyStatus = NewsApplyStatus.APPLY_ONGOING,
                    ""
                )
            )
        )
    }) { banners, cards ->
        val uiModelList = mutableListOf<MainNewsUiModel>().apply {
            add(MainNewsUiModel.Banners(banners))
            cards.forEach {
                add(MainNewsUiModel.Card(it))
            }
        }
        uiModelList
    }

    // 여기서 Banner 랑 MainCard 랑
}