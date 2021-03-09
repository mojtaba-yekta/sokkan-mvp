package com.negahpay.sokkan.framework.net

import com.negahpay.core.utils.Resource
import com.negahpay.sokkan.BuildConfig
import retrofit2.Response
import timber.log.Timber
import java.lang.Exception

abstract class BaseDataSource {
    private val TAG = BaseDataSource::class.qualifiedName
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            Timber.d("$TAG getResult -> ${response.code()}")
            if (response.isSuccessful) {
                val body = response.body()
                Timber.d("$TAG getResult -> $body")
                if (body != null) return Resource.success(body)
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Resource<T> {
        Timber.d("$TAG error -> $message")
        return Resource.error("Network Failed: $message")
    }
}