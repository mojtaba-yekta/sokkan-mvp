package com.negahpay.sokkan.framework.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.negahpay.core.data.BillInquiry
import com.negahpay.core.data.BillInquiryParameter
import com.negahpay.core.utils.Resource
import com.negahpay.sokkan.framework.utils.BillUseCases
import com.negahpay.sokkan.framework.utils.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BillDetailsViewModel @Inject constructor(
    private val billUseCases: BillUseCases,
    private val userUseCases: UserUseCases,
) : ViewModel() {
    private var cellphone = ""
    lateinit var billId: String
    lateinit var senderUniqueRequestId: String
    lateinit var requestedServiceId: String
    lateinit var traceNumber: String

    private val resultData: MediatorLiveData<Resource<BillInquiry>> = MediatorLiveData()
    private val data: LiveData<Resource<BillInquiry>> = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        if (billId.isEmpty())
            emit(Resource.error<BillInquiry>("no bill id"))
        else
            emit(
                billUseCases.receiveBill(
                    BillInquiryParameter(
                        cellphone,
                        senderUniqueRequestId,
                        requestedServiceId,
                        billId,
                        billId,
                        billId,
                        traceNumber
                    )
                )
            )

    }
    private val currentUser = liveData(Dispatchers.IO) {
        emit(userUseCases.getLoggedIn())
    }

    init {
        receiveData()
    }

    fun getData() = resultData

    private fun receiveData() {
        resultData.addSource(currentUser) {
            it?.let { u ->
                cellphone = u.cellphone

                resultData.addSource(data) { b ->
                    resultData.value = b
                }
            }

            resultData.value = Resource.loading()
        }
    }
}