package com.negahpay.sokkan.framework.di

import com.negahpay.core.repository.SettingRepository
import com.negahpay.core.repository.UserRepository
import com.negahpay.core.usecases.*
import com.negahpay.sokkan.framework.UseCases
import dagger.Module
import dagger.Provides

@Module
class UseCasesModule {
    @Provides
    fun providesUseCases(
        userRepo: UserRepository,
        settingRepo: SettingRepository
    ) =
        UseCases(
            AddUser(userRepo),
            GetLoggedIn(userRepo),
            RemoveUser(userRepo),
            GetSetting(settingRepo),
            UpdateSetting(settingRepo)
        )
}