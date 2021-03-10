package com.negahpay.core.repository

import com.negahpay.core.utils.Resource

interface TokenDataSource {
    suspend fun receive(): Resource<String>
}