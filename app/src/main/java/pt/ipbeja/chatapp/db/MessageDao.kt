package pt.ipbeja.chatapp.db

import androidx.room.Dao
import androidx.room.Query

@Dao
interface MessageDao : BaseDao<Message> {

    @Query("select * from message where contactId = :contactId")
    fun getAll(contactId: Long) : List<Message>

}