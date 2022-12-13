package ru.dimagor555.password.data.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.dimagor555.password.repository.ClipboardRepository
import ru.dimagor555.password.repository.AndroidClipboardRepository

actual val clipboardModule = module {
    factoryOf(::AndroidClipboardRepository) bind ClipboardRepository::class
}
