package com.negahpay.core.usecase

import com.negahpay.core.repository.UserRepository

class RemoveUser(private val userRepository: UserRepository) {
    suspend operator fun invoke(cellphone: String) = userRepository.removeUser(cellphone)
}