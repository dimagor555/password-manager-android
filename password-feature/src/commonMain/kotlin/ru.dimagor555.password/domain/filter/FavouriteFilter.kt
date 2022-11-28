package ru.dimagor555.password.domain.filter

sealed class FavouriteFilter {
    abstract fun matches(params: FilterParams): Boolean

    object All : FavouriteFilter() {
        override fun matches(params: FilterParams) = true
    }

    object Favourite : FavouriteFilter() {
        override fun matches(params: FilterParams) = params.isFavourite
    }
}
