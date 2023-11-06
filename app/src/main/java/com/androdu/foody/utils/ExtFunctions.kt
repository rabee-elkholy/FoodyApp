package com.androdu.foody.utils

import androidx.lifecycle.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object :Observer<T>{
        override fun onChanged(t: T) {
            removeObserver(this)
            observer.onChanged(t)
        }
    })
}

fun <T> Flow<T>.safeCollection(lifecycleOwner: LifecycleOwner, data: suspend (T) -> Unit) {
    lifecycleOwner.lifecycleScope.launch {
        this@safeCollection.flowWithLifecycle(lifecycleOwner.lifecycle).collect {
            data(it)
        }
    }
}