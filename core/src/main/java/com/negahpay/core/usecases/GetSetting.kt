package com.negahpay.core.usecases

import com.negahpay.core.repository.SettingRepository

class GetSetting(private val settingRepository: SettingRepository) {
    suspend operator fun invoke(userId: Long) = settingRepository.get(userId)
}