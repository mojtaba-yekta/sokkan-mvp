package com.negahpay.core.usecases.user

import com.negahpay.core.repository.UserRepository

class VerifyUser(private val userRepository: UserRepository) {
    suspend operator fun invoke(cellphone: String, verifyCode: String) =
        userRepository.verifyUser(cellphone, verifyCode)
}