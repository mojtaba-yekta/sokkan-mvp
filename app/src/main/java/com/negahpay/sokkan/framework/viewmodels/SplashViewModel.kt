package com.negahpay.sokkan.framework.viewmodels

import androidx.lifecycle.*
import com.negahpay.core.utils.Resource
import com.negahpay.sokkan.framework.net.NetParams
import com.negahpay.sokkan.framework.utils.TokenUseCases
import com.negahpay.sokkan.framework.utils.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val netParams: NetParams,
    private val userUseCases: UserUseCases,
    private val tokenUseCases: TokenUseCases
) : ViewModel() {
    enum class NavType {
        LOGIN,
        DASHBOARD
    }

    private var isLogin = false
    private val resultData: MediatorLiveData<Resource<String>> = MediatorLiveData()
    private val currentUser = liveData(Dispatchers.IO) {
        emit(userUseCases.getLoggedIn())
    }
    private val token = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        emit(tokenUseCases.receiveToken())
    }

    init {
        receiveToken()
        viewModelScope.launch {
            delay(1000)
        }
    }

    fun receiveToken() {
        resultData.addSource(currentUser) {
            it?.let { u ->
                isLogin = u.isLogin
            }

            resultData.value = Resource.loading()
        }
        resultData.addSource(token) {
            if (it.status == Resource.Status.SUCCESS)
                netParams.token = it.data!!

            resultData.value = it
        }
    }

    fun navType() =
        if (isLogin)
            NavType.DASHBOARD
        else
            NavType.LOGIN

    fun getToken() = resultData
}