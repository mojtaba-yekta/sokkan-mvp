package com.negahpay.sokkan.framework.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface SettingsDao {
    @Insert(onConflict = REPLACE)
    suspend fun update(settingEntity: SettingEntity)

    @Query("SELECT * FROM setting WHERE user_id = :userId")
    fun get(userId: Long): LiveData<SettingEntity?>
}