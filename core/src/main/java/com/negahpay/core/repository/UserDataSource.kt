package com.negahpay.core.repository

import com.negahpay.core.data.User
import com.negahpay.core.utils.Resource

interface UserDataSource {
    suspend fun registerUser(cellphone: String): Resource<String>
    suspend fun verifyUser(cellphone: String, verifyCode: String): Resource<String>
    suspend fun getLoggedIn(): User?
}