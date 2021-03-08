package com.negahpay.sokkan.framework

import android.content.Context
import com.negahpay.core.data.User
import com.negahpay.core.repository.UserDataSource
import com.negahpay.sokkan.framework.db.DatabaseService
import com.negahpay.sokkan.framework.db.UserEntity

class RoomUserDataSource(context: Context) : UserDataSource {
    private val userDao = DatabaseService.getInstance(context).userDao()

    override suspend fun add(user: User) =
        userDao.add(UserEntity.fromUser(user))

    override suspend fun getLoggedIn() =
        userDao.getLoggedIn()?.toUser()

    override suspend fun remove(user: User) =
        userDao.remove(UserEntity.fromUser(user))
}