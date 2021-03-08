package com.negahpay.core.usecases

import com.negahpay.core.data.User
import com.negahpay.core.repository.UserRepository

class RemoveUser(private val userRepository: UserRepository) {
    suspend operator fun invoke(user: User) = userRepository.removeUser(user)
}