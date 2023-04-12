package ru.dimagor555.core.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.Lifecycle
import io.github.aakira.napier.Napier
import kotlinx.coroutines.*
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

fun ComponentContext.componentScope() = ComponentCoroutineScopeDelegate()

class ComponentCoroutineScopeDelegate : ReadOnlyProperty<ComponentContext, CoroutineScope> {

    private val handler = CoroutineExceptionHandler { _, exception ->
        Napier.e("CoroutineExceptionHandler got $exception", exception)
    }
    private val coroutineContext = Dispatchers.Main.immediate + SupervisorJob() + handler
    private var scope = CoroutineScope(coroutineContext)

    private val lifecycleCallback = object : Lifecycle.Callbacks {
        override fun onDestroy() {
            scope.cancel()
        }
    }

    override fun getValue(thisRef: ComponentContext, property: KProperty<*>): CoroutineScope {
        thisRef.lifecycle.run {
            unsubscribe(lifecycleCallback)
            subscribe(lifecycleCallback)
        }
        return scope
    }
}