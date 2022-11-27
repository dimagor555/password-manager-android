package ru.dimagor555.core.presentation

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import kotlinx.coroutines.*
import ru.dimagor555.mvicompose.abstraction.Store

private class StoreInstance<out T : Store<*, *, *>>(
    val store: T,
) : InstanceKeeper.Instance {

    private val handler = CoroutineExceptionHandler { _, exception ->
        println("CoroutineExceptionHandler got $exception")
    }
    private val coroutineScope = CoroutineScope(
        Dispatchers.Main.immediate + SupervisorJob() + handler
    )

    init {
        store.init(coroutineScope)
    }

    override fun onDestroy() {
        coroutineScope.cancel()
    }
}

fun <T : Store<*, *, *>> InstanceKeeper.getStore(key: Any, factory: () -> T): T =
    getOrCreate(key = key) {
        StoreInstance(factory())
    }.store

inline fun <reified T : Store<*, *, *>> InstanceKeeper.getStore(noinline factory: () -> T): T =
    getStore(key = T::class, factory = factory)

