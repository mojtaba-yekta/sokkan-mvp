package com.negahpay.core.repository

class UserRepository(private val dataSource: UserDataSource) {
    suspend fun getLoggedIn() = dataSource.getLoggedIn()
    suspend fun registerUser(cellphone: String) =
        dataSource.registerUser(cellphone)

    suspend fun verifyUser(cellphone: String, verifyCode: String) =
        dataSource.verifyUser(cellphone, verifyCode)

    suspend fun logout() = dataSource.logout()
}