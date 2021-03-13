package com.negahpay.core.usecases.user

import com.negahpay.core.repository.UserRepository

class Logout (private val userRepository: UserRepository){
    suspend operator fun invoke() = userRepository.logout()
}