package com.negahpay.sokkan.framework.viewmodels

import androidx.lifecycle.*
import com.negahpay.core.utils.Resource
import com.negahpay.sokkan.framework.utils.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class VerifyViewModel @Inject constructor(
    private val userUseCases: UserUseCases
) : ViewModel() {
    lateinit var cellphone: String
    private val resultData: MediatorLiveData<Resource<String>> = MediatorLiveData()
    private lateinit var validate: LiveData<Resource<String>>
    private lateinit var countDown: LiveData<Int>
    private lateinit var sendAgainLive: LiveData<Resource<String>>

    fun validateCode(code: String) {
        validate = liveData(Dispatchers.IO) {
            emit(Resource.loading())
            emit(userUseCases.verifyUser(cellphone, code))
        }

        resultData.addSource(validate) {
            resultData.value = it
        }
    }

    fun getValidateState() = resultData
    fun getCountDown() = countDown
    fun getSendAgainLive() = sendAgainLive

    fun sendAgain() {
        sendAgainLive = liveData(Dispatchers.IO) {
            emit(userUseCases.registerUser(cellphone))
        }

        countDown = liveData(Dispatchers.IO) {
            var c = 60
            while (c >= 0) {
                emit(c--)
                delay(1000)
            }
        }
    }

}