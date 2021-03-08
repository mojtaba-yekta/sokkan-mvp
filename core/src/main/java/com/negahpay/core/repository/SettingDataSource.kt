package com.negahpay.core.repository

import com.negahpay.core.data.Setting

interface SettingDataSource {
    suspend fun update(setting: Setting)
    suspend fun get(userId: Long): Setting?
}