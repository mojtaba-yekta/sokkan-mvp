package com.negahpay.sokkan.framework.utils

import com.negahpay.core.usecases.user.*

data class UserUseCases(
    val getLoggedIn: GetLoggedIn,
    val registerUser:RegisterUser,
    val verifyUser:VerifyUser
)