package com.aubrey.jetcomic.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Cart : Screen("cart")
    object Profile : Screen("profile")
    object DetailComic : Screen("home/{comicId}") {
        fun createRoute(comicId: Long) = "home/$comicId"
    }
}