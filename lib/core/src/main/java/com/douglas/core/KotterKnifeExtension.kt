package com.douglas.core

import android.app.Activity
import android.app.Dialog
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

fun <V : View> View.bindView(id: Int):
        ReadOnlyProperty<View, V> = required(id, viewFinder)

fun <V : View> Activity.bindView(id: Int):
        ReadOnlyProperty<Activity, V> = required(id, viewFinder)

fun <V : View> Dialog.bindView(id: Int):
        ReadOnlyProperty<Dialog, V> = required(id, viewFinder)

fun <V : View> FragmentActivity.bindView(id: Int):
        ReadOnlyProperty<FragmentActivity, V> = required(id, viewFinder)

fun <V : View> ViewHolder.bindView(id: Int):
        ReadOnlyProperty<ViewHolder, V> = required(id, viewFinder)

fun <V : View> View.bindOptionalView(id: Int):
        ReadOnlyProperty<View, V?> = optional(id, viewFinder)

fun <V : View> Activity.bindOptionalView(id: Int):
        ReadOnlyProperty<Activity, V?> = optional(id, viewFinder)

fun <V : View> Dialog.bindOptionalView(id: Int):
        ReadOnlyProperty<Dialog, V?> = optional(id, viewFinder)

fun <V : View> FragmentActivity.bindOptionalView(id: Int):
        ReadOnlyProperty<FragmentActivity, V?> = optional(id, viewFinder)

fun <V : View> ViewHolder.bindOptionalView(id: Int):
        ReadOnlyProperty<ViewHolder, V?> = optional(id, viewFinder)

fun <V : View> View.bindViews(vararg ids: Int):
        ReadOnlyProperty<View, List<V>> = required(ids, viewFinder)

fun <V : View> Activity.bindViews(vararg ids: Int):
        ReadOnlyProperty<Activity, List<V>> = required(ids, viewFinder)

fun <V : View> Dialog.bindViews(vararg ids: Int):
        ReadOnlyProperty<Dialog, List<V>> = required(ids, viewFinder)

fun <V : View> FragmentActivity.bindViews(vararg ids: Int):
        ReadOnlyProperty<FragmentActivity, List<V>> = required(ids, viewFinder)

fun <V : View> ViewHolder.bindViews(vararg ids: Int):
        ReadOnlyProperty<ViewHolder, List<V>> = required(ids, viewFinder)

fun <V : View> View.bindOptionalViews(vararg ids: Int):
        ReadOnlyProperty<View, List<V>> = optional(ids, viewFinder)

fun <V : View> Activity.bindOptionalViews(vararg ids: Int):
        ReadOnlyProperty<Activity, List<V>> = optional(ids, viewFinder)

fun <V : View> Dialog.bindOptionalViews(vararg ids: Int):
        ReadOnlyProperty<Dialog, List<V>> = optional(ids, viewFinder)

fun <V : View> FragmentActivity.bindOptionalViews(vararg ids: Int):
        ReadOnlyProperty<FragmentActivity, List<V>> = optional(ids, viewFinder)

fun <V : View> ViewHolder.bindOptionalViews(vararg ids: Int):
        ReadOnlyProperty<ViewHolder, List<V>> = optional(ids, viewFinder)

private val View.viewFinder: View.(Int) -> View?
    get() = { findViewById(it) }
private val Activity.viewFinder: Activity.(Int) -> View?
    get() = { findViewById(it) }
private val Dialog.viewFinder: Dialog.(Int) -> View?
    get() = { findViewById(it) }
private val FragmentActivity.viewFinder: FragmentActivity.(Int) -> View?
    get() = { findViewById(it) }
private val ViewHolder.viewFinder: ViewHolder.(Int) -> View?
    get() = { itemView.findViewById(it) }

private fun viewNotFound(id: Int, desc: KProperty<*>): Nothing =
    throw IllegalStateException("View ID $id for '${desc.name}' not found.")

@Suppress("UNCHECKED_CAST")
private fun <T, V : View> required(id: Int, finder: T.(Int) -> View?) = Lazy { t: T, desc ->
    t.finder(id) as V? ?: viewNotFound(id, desc)
}

@Suppress("UNCHECKED_CAST")
private fun <T, V : View> optional(id: Int, finder: T.(Int) -> View?) = Lazy { t: T, _ -> t.finder(id) as V? }

@Suppress("UNCHECKED_CAST")
private fun <T, V : View> required(ids: IntArray, finder: T.(Int) -> View?) = Lazy { t: T, desc ->
    ids.map {
        t.finder(it) as V? ?: viewNotFound(it, desc)
    }
}

@Suppress("UNCHECKED_CAST")
private fun <T, V : View> optional(ids: IntArray, finder: T.(Int) -> View?) = Lazy { t: T, _ ->
    ids.map {
        t.finder(it) as V?
    }.filterNotNull()
}

class Lazy<T, V>(private val initializer: (T, KProperty<*>) -> V) :
    ReadOnlyProperty<T, V>, LifecycleObserver {
    private object EMPTY

    private var value: Any? = EMPTY
    private var attachedToLifecycleOwner = false

    override fun getValue(thisRef: T, property: KProperty<*>): V {
        checkAddToLifecycleOwner(thisRef)
        if (value == EMPTY) {
            value = initializer(thisRef, property)
        }
        @Suppress("UNCHECKED_CAST")
        return value as V
    }

    private fun checkAddToLifecycleOwner(thisRef: T) {
        if (!attachedToLifecycleOwner && thisRef is LifecycleOwner) {
            thisRef.lifecycle.addObserver(this)
            attachedToLifecycleOwner = true
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun destroy() {
        value = EMPTY
    }
}

fun View.onClick(listenerFunction: () -> Unit) {
    this.setOnClickListener { listenerFunction.invoke() }
}
