package com.aubrey.jetcomic.ui.screen.cart

import com.aubrey.jetcomic.model.OrderComic

data class CartState(
    val orderComic: List<OrderComic>,
    val totalPrice: Int
)
