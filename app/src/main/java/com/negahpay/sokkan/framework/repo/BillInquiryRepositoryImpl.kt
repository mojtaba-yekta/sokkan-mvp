package com.negahpay.sokkan.framework.repo

import com.negahpay.core.data.BillInquiry
import com.negahpay.core.data.BillInquiryParameter
import com.negahpay.core.repository.BillInquiryDataSource
import com.negahpay.core.utils.Resource
import com.negahpay.sokkan.framework.net.*

class BillInquiryRepositoryImpl(
    private val billService: IBillService
) : BillInquiryDataSource, BaseDataSource() {
    override suspend fun receive(param: BillInquiryParameter): Resource<BillInquiry> {
        val res = getResult {
            billService.billInquiry(
                BillInquiryParameterNet.fromBillInquiryParameter(param)
            )
        }

        return if (res.status == Resource.Status.SUCCESS) {
            if (res.data?.returnCode == BILL_DATA_RECEIVED) {
                if (res.data?.returnValue == null)
                    Resource.error("null return value.")
                else
                    Resource.success(res.data!!.returnValue!!.toBillInquiry())
            } else {
                Resource
                    .error(res.data?.returnDescription ?: "null return description.")
            }
        } else {
            Resource.error(res.message ?: "null error data.")
        }
    }
}