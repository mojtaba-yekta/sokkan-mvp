package com.negahpay.sokkan.framework.repo

import com.negahpay.core.data.User
import com.negahpay.core.repository.UserDataSource
import com.negahpay.core.utils.Resource
import com.negahpay.sokkan.framework.db.UserDao
import com.negahpay.sokkan.framework.db.UserEntity
import com.negahpay.sokkan.framework.net.*

class UserRepositoryImpl(
    private val userService: IUserService,
    private val userDao: UserDao
) : UserDataSource, BaseDataSource() {
    override suspend fun registerUser(cellphone: String): Resource<String> {
        val res = getResult { userService.register(cellphone) }
        return if (res.status == Resource.Status.SUCCESS) {
            if (res.data?.returnCode == PHONE_ALREADY_REGISTERED ||
                res.data?.returnCode == PHONE_REGISTERED ||
                res.data?.returnCode == PHONE_REGISTERED_VERIFY_CODE_SENT
            ) {
                userDao.add(
                    UserEntity.fromUser(
                        User(
                            cellphone = cellphone
                        )
                    )
                )

                userService.mobileVerification(cellphone)
                Resource.success(cellphone)
            } else {
                Resource
                    .error(res.data?.returnDescription ?: "null return description.")
            }
        } else {
            Resource.error(res.message ?: "null error data.")
        }
    }

    override suspend fun verifyUser(
        cellphone: String,
        verifyCode: String
    ): Resource<String> {
        val res = getResult { userService.verify(cellphone, verifyCode) }
        return if (res.status == Resource.Status.SUCCESS) {
            if (res.data?.returnCode == USER_ALREADY_ACTIVATED ||
                res.data?.returnCode == USER_ACTIVATED
            ) {
                userDao.add(
                    UserEntity.fromUser(
                        User(
                            cellphone = cellphone, isLogin = true
                        )
                    )
                )
                Resource.success(cellphone)
            } else {
                Resource
                    .error(res.data?.returnDescription ?: "null return description.")
            }
        } else {
            Resource.error(res.message ?: "null error data.")
        }
    }

    override suspend fun getLoggedIn(): User? =
        userDao.getLoggedIn()?.toUser()

    override suspend fun logout() =
        userDao.logout()

}