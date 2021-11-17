package pt.ipbeja.chatapp.db

import androidx.room.*

@Dao
interface MessageDao : BaseDao<Message> {

    @Query("select * from message where contactId = :contactId")
    fun getAll(contactId: Long) : List<Message>

}