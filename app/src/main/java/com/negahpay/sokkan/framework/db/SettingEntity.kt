package com.negahpay.sokkan.framework.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.negahpay.core.data.Setting

@Entity(tableName = "setting")
data class SettingEntity(
    @ColumnInfo(name = "user_id")
    @PrimaryKey(autoGenerate = false)
    val userId: Long = 0L,
    @ColumnInfo(name = "api_key")
    val apiKey: String
) {
    fun toSetting() = Setting(userId, apiKey)

    companion object {
        fun fromSetting(setting: Setting) =
            SettingEntity(setting.userId, setting.apiKey)
    }
}