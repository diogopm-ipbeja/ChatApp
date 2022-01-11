package pt.ipbeja.chatapp.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import pt.ipbeja.chatapp.db.ChatDB
import pt.ipbeja.chatapp.db.Contact
import pt.ipbeja.chatapp.db.ContactDao
import pt.ipbeja.chatapp.utils.TAG

class ContactsViewModel(app: Application) : AndroidViewModel(app) {

    private val dao: ContactDao = ChatDB(app).contactDao()

    var contacts: List<Contact> = dao.getAll()
        private set


    init {
        Log.i(TAG, "ContactsViewModel Created!")
    }

    fun createContact(contact: Contact) {
        dao.insert(contact)
        refreshContacts()
    }

    fun refreshContacts() {
        contacts = dao.getAll()
    }

}