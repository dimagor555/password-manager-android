package ru.dimagor555.core.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.Lifecycle
import kotlinx.coroutines.*
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

fun ComponentContext.componentScope() = ComponentCoroutineScopeDelegate()

class ComponentCoroutineScopeDelegate : ReadOnlyProperty<ComponentContext, CoroutineScope> {

    private val handler = CoroutineExceptionHandler { _, exception ->
        println("CoroutineExceptionHandler got $exception")
    }
    private val coroutineContext = Dispatchers.Main.immediate + SupervisorJob() + handler
    private var scope: CoroutineScope? = null

    private val lifecycleCallback = object : Lifecycle.Callbacks {
        override fun onCreate() {
            scope = CoroutineScope(coroutineContext)
        }

        override fun onDestroy() {
            scope?.cancel()
            scope = null
        }
    }

    override fun getValue(thisRef: ComponentContext, property: KProperty<*>): CoroutineScope {
        thisRef.lifecycle.run {
            unsubscribe(lifecycleCallback)
            subscribe(lifecycleCallback)
        }
        return scope!!
    }
}