package pt.ipbeja.chatapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao : BaseDao<Contact> {

    @Query("select * from contact")
    fun getAll() : List<Contact>

    @Query("select * from contact")
    fun getAllLiveData() : LiveData<List<Contact>>

    @Query("select * from contact")
    fun getAllFlow() : Flow<List<Contact>>





    @Query("select * from contact where id = :contactId")
    fun get(contactId: Long) : Contact

    @Query("delete from contact")
    fun deleteAll() : Int
}