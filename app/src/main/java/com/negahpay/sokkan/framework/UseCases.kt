package com.negahpay.sokkan.framework

import com.negahpay.core.usecases.*

data class UseCases(
    val addUser: AddUser,
    val getLoggedIn: GetLoggedIn,
    val removeUser: RemoveUser,
    val getSetting: GetSetting,
    val updateSetting: UpdateSetting
)