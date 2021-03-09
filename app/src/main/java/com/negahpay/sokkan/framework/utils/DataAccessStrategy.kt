package com.negahpay.sokkan.framework.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.negahpay.core.utils.Resource
import kotlinx.coroutines.Dispatchers

fun <T, A> performGetOperation(
    databaseQuery: () -> LiveData<T>,
    networkCall: suspend () -> Resource<A>,
    saveCallResult: suspend (A) -> Unit
): LiveData<Resource<T>> =
    liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val source = databaseQuery.invoke().map { Resource.success(it) }
        emitSource(source)

        val response = networkCall.invoke()
        if (response.status == Resource.Status.SUCCESS) {
            saveCallResult(response.data!!)
        } else if (response.status == Resource.Status.ERROR) {
            emit(Resource.error(response.message!!))
            emitSource(source)
        }
    }

fun <T> performGetLocalOperation(
    databaseQuery: () -> LiveData<T>
): LiveData<Resource<T>> =
    liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val source = databaseQuery.invoke().map { Resource.success(it) }
        emitSource(source)
    }