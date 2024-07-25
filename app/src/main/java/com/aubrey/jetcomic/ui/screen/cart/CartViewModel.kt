package com.aubrey.jetcomic.ui.screen.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aubrey.jetcomic.data.ComicRepository
import com.aubrey.jetcomic.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartViewModel (private val repository: ComicRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<CartState>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<CartState>>
        get() = _uiState

    fun getAddedOrderComics() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getAddedOrderComics()
                .collect { orderComic ->
                    val totalRequiredPoint =
                        orderComic.sumOf { it.comic.price * it.count }
                    _uiState.value = UiState.Success(CartState(orderComic, totalRequiredPoint))
                }
        }
    }

    fun updateOrderComic(romicId: Long, count: Int) {
        viewModelScope.launch {
            repository.updateOrderComic(romicId, count)
                .collect { isUpdated ->
                    if (isUpdated) {
                        getAddedOrderComics()
                    }
                }
        }
    }
}