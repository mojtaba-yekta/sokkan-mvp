package com.negahpay.core.repository

import com.negahpay.core.data.User

class UserRepository(private val dataSource: UserDataSource) {
    suspend fun addUser(user: User) = dataSource.add(user)
    suspend fun getLoggedIn() = dataSource.getLoggedIn()
    suspend fun removeUser(cellphone: String) = dataSource.remove(cellphone)
}