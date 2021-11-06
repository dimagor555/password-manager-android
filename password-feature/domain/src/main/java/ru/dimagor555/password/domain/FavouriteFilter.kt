package ru.dimagor555.password.domain

sealed class FavouriteFilter {
    abstract fun matches(password: Password): Boolean

    object All : FavouriteFilter() {
        override fun matches(password: Password) = true
    }

    object Favourite : FavouriteFilter() {
        override fun matches(password: Password) = password.isFavourite
    }
}