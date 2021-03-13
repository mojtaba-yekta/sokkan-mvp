package com.negahpay.sokkan.framework.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.negahpay.core.data.User
import com.negahpay.sokkan.framework.utils.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val userUseCases: UserUseCases
) : ViewModel() {
    private lateinit var isLogout: LiveData<Boolean>
    private val user: LiveData<User> = liveData(Dispatchers.IO) {
        val u = userUseCases.getLoggedIn()
        if (u == null)
            logout()
        else
            emit(u)
    }

    fun getIsLogout() = isLogout
    fun getUser() = user

    fun logout() {
        isLogout = liveData(Dispatchers.IO) {
            userUseCases.logout()
            emit(true)
        }
    }

}