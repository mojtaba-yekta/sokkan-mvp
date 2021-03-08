package com.negahpay.core.repository

import com.negahpay.core.data.Setting

class SettingRepository(private val settings: SettingDataSource) {
    suspend fun update(setting: Setting) = settings.update(setting)
    suspend fun get(userId: Long) = settings.get(userId)
}