package com.negahpay.sokkan.framework

import android.content.Context
import com.negahpay.core.data.Setting
import com.negahpay.core.repository.SettingDataSource
import com.negahpay.sokkan.framework.db.DatabaseService
import com.negahpay.sokkan.framework.db.SettingEntity

class RoomSettingDataSource(context: Context) : SettingDataSource {
    private val settingDao = DatabaseService.getInstance(context).settingDao()

    override suspend fun update(setting: Setting) =
        settingDao.update(SettingEntity.fromSetting(setting))

    override suspend fun get(userId: Long) =
        settingDao.get(userId)?.toSetting()
}