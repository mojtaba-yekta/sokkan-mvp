package com.negahpay.sokkan.framework

import androidx.lifecycle.*
import com.negahpay.core.data.Setting
import com.negahpay.core.utils.Resource
import com.negahpay.sokkan.framework.net.NetParams
import com.negahpay.sokkan.framework.repo.SettingsRepository
import com.negahpay.sokkan.framework.repo.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val userRepository: UserRepository,
    private val netParams: NetParams
) : ViewModel() {

    enum class NavType {
        LOGIN,
        DASHBOARD
    }

    private var isLogin: Boolean = false
    private var userId: Long = 0

    val currentUser = userRepository.getLoggedIn()
    var setting: LiveData<Resource<Setting>> = currentUser.switchMap {
        isLogin = it.data!!.isLogin
        userId = it.data!!.id
        settingsRepository.getSettings(userId)
    }

    init {
        viewModelScope.launch {
            delay(1000)
        }
    }

    fun recieveToken() {
        setting = settingsRepository.getSettings(userId)
    }

    fun navType() =
        if (isLogin)
            NavType.DASHBOARD
        else
            NavType.LOGIN
}