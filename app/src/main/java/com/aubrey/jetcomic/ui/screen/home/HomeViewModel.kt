package com.aubrey.jetcomic.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aubrey.jetcomic.data.ComicRepository
import com.aubrey.jetcomic.model.Comic
import com.aubrey.jetcomic.model.OrderComic
import com.aubrey.jetcomic.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: ComicRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<OrderComic>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<OrderComic>>>
        get() = _uiState


    fun getAllComics() {
        viewModelScope.launch {
            repository.getAllComics()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { orderComics ->
                    _uiState.value = UiState.Success(orderComics)
                }
        }
    }


    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun search(newQuery: String) {
        viewModelScope.launch {
            _query.value = newQuery
            repository.searchComic(newQuery)
                .catch { throwable ->
                    _uiState.value = UiState.Error(throwable.message.toString())
                }
                .collect { comics ->
                    _uiState.value = UiState.Success(comics.map { OrderComic(it, 0) })
                }
        }
    }
}