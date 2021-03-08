package com.negahpay.sokkan.framework.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.negahpay.core.data.User

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val cellphone: String,
    @ColumnInfo(name = "is_login")
    val isLogin: Boolean
) {
    fun toUser() = User(id, cellphone, isLogin)

    companion object {
        fun fromUser(user: User) = UserEntity(
            user.id,
            user.cellphone,
            user.isLogin
        )
    }
}
