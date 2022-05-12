package com.github.rahul_gill.recurrence.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun <T, K> Flow<T>.groupToList(getKey: (T) -> K): Flow<Pair<K, List<T>>> = flow {
    val storage = mutableMapOf<K, MutableList<T>>()
    collect { t -> storage.getOrPut(getKey(t)) { mutableListOf() } += t }
    storage.forEach { (k, ts) -> emit(k to ts) }
}