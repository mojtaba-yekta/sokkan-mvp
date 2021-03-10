package com.negahpay.sokkan.framework.repo

import com.negahpay.core.repository.TokenDataSource
import com.negahpay.core.utils.Resource
import com.negahpay.sokkan.framework.mem.SharedPrefService
import com.negahpay.sokkan.framework.net.BaseDataSource
import com.negahpay.sokkan.framework.net.ITokenService
import com.negahpay.sokkan.framework.net.TOKEN_CREATED
import com.negahpay.sokkan.framework.net.TOKEN_IS_ACTIVE

class TokenRepositoryImpl(
    private val tokenService: ITokenService,
    private val sharedPrefService: SharedPrefService
) : TokenDataSource, BaseDataSource() {
    override suspend fun receive(): Resource<String> {
        val res = getResult { tokenService.getToken() }
        return if (res.status == Resource.Status.SUCCESS) {
            if (res.data?.returnCode == TOKEN_CREATED ||
                res.data?.returnCode == TOKEN_IS_ACTIVE
            ) {
                val t = res.data?.returnValue?:""
                sharedPrefService.token(t)
                Resource.success(t)
            } else {
                Resource
                    .error(res.data?.returnDescription ?: "null return description.")
            }
        } else {
            Resource.error(res.message ?: "null error data.")
        }
    }
}