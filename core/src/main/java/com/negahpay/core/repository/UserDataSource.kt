package com.negahpay.core.repository

import com.negahpay.core.data.User

interface UserDataSource {
    suspend fun add(user: User)
    suspend fun getLoggedIn(): User?
    suspend fun remove(user: User)
}