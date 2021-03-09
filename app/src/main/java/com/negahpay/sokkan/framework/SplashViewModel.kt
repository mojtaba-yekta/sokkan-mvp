package com.negahpay.sokkan.framework

import androidx.lifecycle.*
import com.negahpay.core.data.Setting
import com.negahpay.core.data.User
import com.negahpay.core.utils.Resource
import com.negahpay.sokkan.framework.net.NetParams
import com.negahpay.sokkan.framework.repo.SettingsRepository
import com.negahpay.sokkan.framework.repo.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val userRepository: UserRepository,
    private val netParams: NetParams
) : ViewModel() {
    private val TAG = SplashViewModel::class.qualifiedName

    enum class NavType {
        LOGIN,
        DASHBOARD
    }

    private var isLogin: Boolean = false
    private var userId: Long = 0

    private var currentUser: LiveData<Resource<User>> = userRepository.getLoggedIn()

    var setting: LiveData<Resource<Setting>> = currentUser.switchMap {
        isLogin = it.data!!.isLogin
        userId = it.data!!.id
        settingsRepository.getSettings(userId)
    }

    init {
        Timber.d("$TAG init -> $userId")
        receiveToken()
        viewModelScope.launch {
            delay(1000)
        }
    }

    fun receiveToken() {
        setting = settingsRepository.getSettings(userId)
        setting.map {
            netParams.token = it.data?.apiKey ?: ""
            Timber.d("$TAG setting.map -> $netParams")
        }
        Timber.d("$TAG receiveToken -> $netParams")
    }

    fun navType() =
        if (isLogin)
            NavType.DASHBOARD
        else
            NavType.LOGIN
}