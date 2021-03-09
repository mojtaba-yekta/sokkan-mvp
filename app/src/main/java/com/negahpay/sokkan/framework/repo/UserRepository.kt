package com.negahpay.sokkan.framework.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.negahpay.core.data.User
import com.negahpay.core.utils.Resource
import com.negahpay.sokkan.framework.db.UserDao
import com.negahpay.sokkan.framework.utils.performGetLocalOperation
import timber.log.Timber
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val db: UserDao
) {
    private val TAG = UserRepository::class.qualifiedName
    fun getLoggedIn(): LiveData<Resource<User>> {
        Timber.d("$TAG getLoggedIn -> called.")
        return performGetLocalOperation {
            db.getLoggedIn().map { it?.toUser() ?: User() }
        }
    }
}