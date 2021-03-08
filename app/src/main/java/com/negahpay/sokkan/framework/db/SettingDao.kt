package com.negahpay.sokkan.framework.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface SettingDao {
    @Insert(onConflict = REPLACE)
    suspend fun update(settingEntity: SettingEntity)

    @Query("SELECT * FROM setting WHERE user_id = :userId")
    suspend fun get(userId: Long): SettingEntity?
}