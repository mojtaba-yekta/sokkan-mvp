package com.negahpay.sokkan.framework.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = REPLACE)
    suspend fun add(userEntity: UserEntity)

    @Query("SELECT * FROM user WHERE is_login = 1 LIMIT 1")
    suspend fun getLoggedIn(): UserEntity?

    @Delete
    suspend fun remove(userEntity: UserEntity)
}