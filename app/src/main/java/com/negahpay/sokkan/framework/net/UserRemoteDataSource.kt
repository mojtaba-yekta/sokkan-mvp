package com.negahpay.sokkan.framework.net

import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val userService: UserService
):BaseDataSource(){
    suspend fun register(mobile:String) =
        getResult { userService.register(mobile) }
}