package com.negahpay.core.usecase

import com.negahpay.core.repository.UserRepository

class GetLoggedIn(private val userRepository: UserRepository) {
    suspend operator fun invoke() = userRepository.getLoggedIn()
}