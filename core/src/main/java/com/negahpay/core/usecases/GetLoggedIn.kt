package com.negahpay.core.usecases

import com.negahpay.core.repository.UserRepository

class GetLoggedIn(private val userRepository: UserRepository) {
    suspend operator fun invoke() = userRepository.getLoggedIn()
}