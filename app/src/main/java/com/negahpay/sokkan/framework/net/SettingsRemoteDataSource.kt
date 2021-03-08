package com.negahpay.sokkan.framework.net

import javax.inject.Inject

class SettingsRemoteDataSource @Inject constructor(
    private val settingsService: SettingsService
) : BaseDataSource() {
    suspend fun getApiKey() = getResult { settingsService.getApiKey() }
}