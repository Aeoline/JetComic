package com.aubrey.jetcomic.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aubrey.jetcomic.data.ComicRepository
import com.aubrey.jetcomic.ui.screen.cart.CartViewModel
import com.aubrey.jetcomic.ui.screen.detail.DetailComicViewModel
import com.aubrey.jetcomic.ui.screen.home.HomeViewModel

class ViewModelFactory (private val repository: ComicRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(DetailComicViewModel::class.java)) {
            return DetailComicViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}