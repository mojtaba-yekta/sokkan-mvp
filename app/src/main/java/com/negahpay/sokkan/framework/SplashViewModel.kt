package com.negahpay.sokkan.framework

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.negahpay.core.data.Setting
import com.negahpay.core.data.User
import com.negahpay.core.repository.SettingRepository
import com.negahpay.core.repository.UserRepository
import com.negahpay.core.usecases.*
import com.negahpay.sokkan.framework.di.ApplicationModule
import com.negahpay.sokkan.framework.di.DaggerViewModelComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashViewModel(application: Application) : AndroidViewModel(application) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    @Inject
    lateinit var useCases: UseCases

    lateinit var currentUser: User
    val hasApiKey = MutableLiveData(false)
    val errorGettingApiKey = MutableLiveData(false)

    init {
        DaggerViewModelComponent
            .builder()
            .applicationModule(ApplicationModule(application))
            .build()
            .inject(this)

        coroutineScope.launch {
            delay(1000)
            val user = useCases.getLoggedIn()
            (user ?: User(0, "", false)).also { currentUser = it }
            receiveApiKey()
        }
    }

    fun receiveApiKey() {
        //todo get api key from net
        if (true) {
            saveApiKey("test")
        } else {
            errorGettingApiKey.postValue(true)
        }
    }

    private fun saveApiKey(key: String) {
        coroutineScope.launch {
            useCases.updateSetting(Setting(currentUser.id, key))
            hasApiKey.postValue(true)
        }
    }
}