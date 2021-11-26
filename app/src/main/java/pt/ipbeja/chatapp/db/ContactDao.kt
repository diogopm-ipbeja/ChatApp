package pt.ipbeja.chatapp.db

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ContactDao : BaseDao<Contact> {

    @Query("select * from contact")
    fun getAll() : List<Contact>

    @Query("select * from contact where id = :contactId")
    fun get(contactId: Long) : Contact

    @Query("delete from contact")
    fun deleteAll() : Int
}