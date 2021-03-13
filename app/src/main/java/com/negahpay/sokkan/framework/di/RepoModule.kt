package com.negahpay.sokkan.framework.di

import com.negahpay.core.repository.*
import com.negahpay.core.usecases.billinquiry.ReceiveBill
import com.negahpay.core.usecases.token.ReceiveToken
import com.negahpay.core.usecases.user.*
import com.negahpay.sokkan.framework.db.UserDao
import com.negahpay.sokkan.framework.mem.SharedPrefService
import com.negahpay.sokkan.framework.net.IBillService
import com.negahpay.sokkan.framework.net.ITokenService
import com.negahpay.sokkan.framework.net.IUserService
import com.negahpay.sokkan.framework.repo.BillInquiryRepositoryImpl
import com.negahpay.sokkan.framework.repo.TokenRepositoryImpl
import com.negahpay.sokkan.framework.repo.UserRepositoryImpl
import com.negahpay.sokkan.framework.utils.BillUseCases
import com.negahpay.sokkan.framework.utils.TokenUseCases
import com.negahpay.sokkan.framework.utils.UserUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object RepoModule {
    @Provides
    fun provideTokenRepo(
        tokenService: ITokenService,
        sharedPrefService: SharedPrefService
    ): TokenDataSource = TokenRepositoryImpl(tokenService, sharedPrefService)

    @Provides
    fun provideUserRepository(
        userService: IUserService,
        userDao: UserDao
    ): UserDataSource = UserRepositoryImpl(userService, userDao)

    @Provides
    fun provideUserUseCases(
        userRepo: UserDataSource
    ) =
        UserUseCases(
            GetLoggedIn(UserRepository(userRepo)),
            RegisterUser(UserRepository(userRepo)),
            VerifyUser(UserRepository(userRepo)),
            Logout(UserRepository(userRepo))
        )

    @Provides
    fun provideTokenUseCases(
        tokenRepo: TokenDataSource
    ) =
        TokenUseCases(
            ReceiveToken(TokenRepository(tokenRepo))
        )

    @Provides
    fun provideBillInquiryRepository(
        billService: IBillService
    ): BillInquiryDataSource = BillInquiryRepositoryImpl(billService)

    @Provides
    fun provideBillUseCases(
        billInquiryRepo: BillInquiryDataSource
    ) =
        BillUseCases(
            ReceiveBill(BillInquiryRepository(billInquiryRepo))
        )

}