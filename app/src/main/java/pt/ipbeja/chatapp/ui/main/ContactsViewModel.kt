package pt.ipbeja.chatapp.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import pt.ipbeja.chatapp.db.ChatDB
import pt.ipbeja.chatapp.db.Contact
import pt.ipbeja.chatapp.db.ContactDao
import pt.ipbeja.chatapp.utils.TAG

class ContactsViewModel(app: Application) : AndroidViewModel(app) {

    private val dao: ContactDao = ChatDB(app).contactDao()

    /*var contacts: List<Contact> = dao.getAll()
        private set*/


    var contactsLiveData: LiveData<List<Contact>> = dao.getAllLiveData()
        private set

    var contactsFlow: Flow<List<Contact>> = dao.getAllFlow()
        private set




    init {
        Log.i(TAG, "ContactsViewModel Created!")
    }


    /*fun refreshContacts() {
        contacts = dao.getAll()
    }*/

}