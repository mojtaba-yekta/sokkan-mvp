package com.negahpay.sokkan.framework.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.negahpay.core.utils.Resource
import com.negahpay.sokkan.framework.net.NetParams
import com.negahpay.sokkan.framework.utils.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userUseCases: UserUseCases
) : ViewModel() {
    private val resultData: MediatorLiveData<Resource<String>> = MediatorLiveData()
    private lateinit var register: LiveData<Resource<String>>

    fun sendVerifyCode(cellphone: String) {
        register = liveData(Dispatchers.IO) {
            emit(Resource.loading())
            emit(userUseCases.registerUser(cellphone))
        }

        resultData.addSource(register) {
            resultData.value = it
        }
    }

    fun getVerifyStat() = resultData

}