package ru.dimagor555.password.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.dimagor555.password.data.AndroidClipboardRepository
import ru.dimagor555.password.usecase.field.repository.ClipboardRepository

actual val passwordPlatformModule = module {
    factoryOf(::AndroidClipboardRepository) bind ClipboardRepository::class
}
