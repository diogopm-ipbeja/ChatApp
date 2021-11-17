package pt.ipbeja.chatapp.db

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

interface BaseDao<T> {

    @Insert
    fun insert(data: T) : Long

    @Insert
    fun insert(data: List<T>) : List<Long>

    @Delete
    fun delete(data: T) : Int // affected records

    @Delete
    fun delete(data: List<T>) : Int // affected records

    @Update
    fun update(data: T) : Int // affected records

    @Update
    fun update(data: List<T>) : Int // affected records

}