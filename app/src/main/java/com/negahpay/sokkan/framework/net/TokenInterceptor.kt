package com.negahpay.sokkan.framework.net

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor @Inject constructor(
    private val netParams: NetParams,
    private val token: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val requestBuilder = request.newBuilder()

        if (request.header(netParams.apiKeyHeader) == null) {
            if (token.isEmpty()) {
                throw RuntimeException("Session token should be defined for auth apis")
            } else {
                requestBuilder.addHeader(netParams.tokenHeader, token)
            }
        }

        return chain.proceed(requestBuilder.build())
    }
}