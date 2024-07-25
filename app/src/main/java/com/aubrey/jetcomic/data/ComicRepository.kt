package com.aubrey.jetcomic.data

import com.aubrey.jetcomic.model.Comic
import com.aubrey.jetcomic.model.ComicsData
import com.aubrey.jetcomic.model.OrderComic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class ComicRepository {

    private val comic: List<Comic> = ComicsData.comics
    private val orderComics = mutableListOf<OrderComic>()

    init {
        if (orderComics.isEmpty()) {
            ComicsData.comics.forEach {
                orderComics.add(OrderComic(it, 0))
            }
        }
    }

    fun getAllComics(): Flow<List<OrderComic>> {
        return flowOf(orderComics)
    }


    fun searchComic(query: String): Flow<List<Comic>> {
        return flow {
            val filteredList = orderComics.filter { it.comic.title.contains(query, ignoreCase = true) }
                .map { it.comic }
            emit(filteredList)
        }
    }

    fun getAddedOrderComics(): Flow<List<OrderComic>> {
        return getAllComics()
            .map { orderComics ->
                orderComics.filter { orderComic ->
                    orderComic.count != 0
                }
            }
    }

    fun getOrderComicById(comicId: Long): OrderComic {
        return orderComics.first {
            it.comic.id == comicId
        }
    }

    fun updateOrderComic(comicId: Long, newCountValue: Int): Flow<Boolean> {
        val index = orderComics.indexOfFirst { it.comic.id == comicId }
        val result = if (index >= 0) {
            val orderComic = orderComics[index]
            orderComics[index] =
                orderComic.copy(comic = orderComic.comic, count = newCountValue)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    companion object {
        @Volatile
        private var instance: ComicRepository? = null

        fun getInstance(): ComicRepository =
            instance ?: synchronized(this) {
                ComicRepository().apply {
                    instance = this
                }
            }
    }
}