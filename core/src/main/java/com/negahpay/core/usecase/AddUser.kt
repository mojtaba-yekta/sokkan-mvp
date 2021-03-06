package com.negahpay.core.usecase

import com.negahpay.core.data.User
import com.negahpay.core.repository.UserRepository

class AddUser(private val userRepository: UserRepository) {
    suspend operator fun invoke(user: User) = userRepository.addUser(user)
}