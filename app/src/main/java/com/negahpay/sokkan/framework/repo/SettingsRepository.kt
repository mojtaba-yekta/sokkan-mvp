package com.negahpay.sokkan.framework.repo

import androidx.lifecycle.map
import com.negahpay.core.data.Setting
import com.negahpay.sokkan.framework.db.SettingEntity
import com.negahpay.sokkan.framework.db.SettingsDao
import com.negahpay.sokkan.framework.net.SettingsRemoteDataSource
import com.negahpay.sokkan.framework.utils.performNetFirstOperation
import javax.inject.Inject

class SettingsRepository @Inject constructor(
    private val remote: SettingsRemoteDataSource,
    private val db: SettingsDao
) {
    fun getSettings(userId: Long) = performNetFirstOperation(
        networkCall = { remote.getApiKey() },
        saveCallResult = {
            if (it.returnCode == 2001 || it.returnCode == 2004) {
                db.update(SettingEntity(userId, it.returnValue))
            }
            db.get(userId).map { s -> s?.toSetting()?: Setting() }
        }
    )
}