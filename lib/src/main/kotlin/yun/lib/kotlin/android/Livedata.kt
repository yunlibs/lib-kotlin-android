package yun.lib.kotlin.android

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations

fun <T, R> LiveData<T>.map(mapper: (T) -> R): LiveData<R> = Transformations.map(this) { mapper.invoke(value as T) }

fun <T, R> LiveData<T>.switchMap(mapper: (T) -> MutableLiveData<R>): LiveData<R> = Transformations.switchMap(this) { mapper.invoke(value as T) }

private fun <T> LiveData<T>.filter(filter: (T, T) -> Boolean) = MediatorLiveData<T>().apply { addSource(this@filter) { newValue -> if (filter(value as T, newValue as T)) postValue(newValue) } }

fun <T> LiveData<T>.filter(filter: (T) -> Boolean) = filter { _, new -> filter.invoke(new) }

fun <T> LiveData<T>.nonNull() = filter { _, new -> new != null }

fun <T> LiveData<T>.distinct() = filter { old, new -> new != old }