package com.negahpay.sokkan.framework.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.negahpay.core.data.User

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    val cellphone: String,
    @ColumnInfo(name = "is_login")
    val isLogin: Boolean
) {
    fun toUser() = User(cellphone, isLogin)

    companion object {
        fun fromUser(user: User) = UserEntity(
            user.cellphone,
            user.isLogin
        )
    }
}
