package com.aubrey.jetcomic.di

import com.aubrey.jetcomic.data.ComicRepository

object Injection {
    fun provideRepository(): ComicRepository {
        return ComicRepository.getInstance()
    }
}