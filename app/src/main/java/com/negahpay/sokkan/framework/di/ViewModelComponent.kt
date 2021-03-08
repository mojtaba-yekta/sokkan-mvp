package com.negahpay.sokkan.framework.di

import com.negahpay.sokkan.framework.SplashViewModel
import dagger.Component

@Component(
    modules = [
        RepositoryModule::class,
        UseCasesModule::class,
        ApplicationModule::class,
        NetworkModule::class
    ]
)
interface ViewModelComponent {
    fun inject(splashViewModel: SplashViewModel)
}