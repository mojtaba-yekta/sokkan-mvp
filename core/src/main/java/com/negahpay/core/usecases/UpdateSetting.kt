package com.negahpay.core.usecases

import com.negahpay.core.data.Setting
import com.negahpay.core.repository.SettingRepository

class UpdateSetting(private val settingRepository: SettingRepository) {
    suspend operator fun invoke(setting: Setting) = settingRepository.update(setting)
}