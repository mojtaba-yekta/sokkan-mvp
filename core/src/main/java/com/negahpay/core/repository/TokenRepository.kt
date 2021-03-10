package com.negahpay.core.repository

class TokenRepository(
    private val tokenDataSource: TokenDataSource
) {
    suspend fun receive() = tokenDataSource.receive()
}