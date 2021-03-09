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
    private lateinit var setting: LiveData<Resource<Setting>>
    private val resultData:MediatorLiveData<Resource<Setting>> = MediatorLiveData()
    private var isLogin = false

    enum class NavType {
        LOGIN,
        DASHBOARD
    }

    init {
        receiveToken()
        viewModelScope.launch {
            delay(1000)
        }
    }

    fun receiveToken() {
        setting = userRepository.getLoggedIn().switchMap {
            settingsRepository.getSettings(it.data?.id ?: 0)
        }

        resultData.addSource(setting){
            netParams.token = it.data?.apiKey?:""
            Timber.d("$TAG setting changed")
            resultData.value = it
        }
    }

    fun navType() =
        if (isLogin)
            NavType.DASHBOARD
        else
            NavType.LOGIN

    fun getSettings() = resultData
}