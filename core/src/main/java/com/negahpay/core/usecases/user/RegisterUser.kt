package com.negahpay.core.usecases.user

import com.negahpay.core.repository.UserRepository

class RegisterUser(private val userRepository: UserRepository) {
    suspend operator fun invoke(cellphone: String) =
        userRepository.registerUser(cellphone)
}