package com.tripbook.tripbook.viewmodel

import com.tripbook.tripbook.TripBookBaseViewModel
import com.tripbook.tripbook.domain.usecase.GetNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsMainViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase
): TripBookBaseViewModel() {

//    val mainItem: StateFlow<List<MainNewsUiModel>> = getNewsUseCase().stateIn(
//        scope = viewModelScope,
//        started = SharingStarted.WhileSubscribed(5000),
//        initialValue = emptyList()
//    )

}