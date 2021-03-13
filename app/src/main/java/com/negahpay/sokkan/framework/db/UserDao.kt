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
    fun getLoggedIn(): UserEntity?

    @Delete
    suspend fun remove(userEntity: UserEntity)

    @Query("SELECT * FROM user WHERE cellphone = :cellphone")
    suspend fun get(cellphone: String): UserEntity?

    @Query("UPDATE user SET is_login = 0")
    suspend fun logout()
}