package com.negahpay.sokkan.framework.repo

import androidx.lifecycle.map
import com.negahpay.core.data.User
import com.negahpay.sokkan.framework.db.UserDao
import com.negahpay.sokkan.framework.utils.performGetLocalOperation
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val db: UserDao
) {
    fun getLoggedIn() =
        performGetLocalOperation {
            db.getLoggedIn().map { it?.toUser() ?: User() }
        }
}