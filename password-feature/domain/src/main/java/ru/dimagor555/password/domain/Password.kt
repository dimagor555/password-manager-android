package ru.dimagor555.password.domain

data class Password(
    val id: Int? = null,
    val encryptedPassword: EncryptedPassword,
    val dateTimeMetadata: DateTimeMetadata = DateTimeMetadata(),
    val usageHistory: UsageHistory = UsageHistory()
) {
    var isFavourite = false
        private set

    fun toggleFavourite() {
        isFavourite = !isFavourite
    }
}
