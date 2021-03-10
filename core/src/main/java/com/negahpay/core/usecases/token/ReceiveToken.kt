package com.negahpay.core.usecases.token

import com.negahpay.core.repository.TokenRepository

class ReceiveToken(
    private val tokenRepository: TokenRepository
) {
    suspend operator fun invoke() = tokenRepository.receive()
}