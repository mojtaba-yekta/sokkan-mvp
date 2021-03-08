package com.negahpay.sokkan.framework.repo

import com.negahpay.sokkan.framework.db.SettingEntity
import com.negahpay.sokkan.framework.db.SettingsDao
import com.negahpay.sokkan.framework.net.SettingsRemoteDataSource
import com.negahpay.sokkan.framework.utils.performGetOperation
import javax.inject.Inject

class SettingsRepository @Inject constructor(
    private val remote: SettingsRemoteDataSource,
    private val db: SettingsDao
) {
    fun getSettings(userId: Long) = performGetOperation(
        databaseQuery = { db.get(userId) },
        networkCall = { remote.getApiKey() },
        saveCallResult = {
            if (it.returnCode == 2001) {
                db.update(SettingEntity(userId, it.returnValue))
            }
        }
    )
}