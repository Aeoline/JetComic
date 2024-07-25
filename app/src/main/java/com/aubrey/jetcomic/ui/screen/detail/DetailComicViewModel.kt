package com.aubrey.jetcomic.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aubrey.jetcomic.data.ComicRepository
import com.aubrey.jetcomic.model.Comic
import com.aubrey.jetcomic.model.OrderComic
import com.aubrey.jetcomic.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailComicViewModel(
    private val repository: ComicRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<OrderComic>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<OrderComic>>
        get() = _uiState

    fun getComicById(comicId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getOrderComicById(comicId))
        }
    }

    fun addToCart(comic: Comic, count: Int) {
        viewModelScope.launch {
            repository.updateOrderComic(comic.id, count)
        }
    }
}