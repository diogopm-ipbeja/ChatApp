package pt.ipbeja.chatapp.ui.createcontact

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pt.ipbeja.chatapp.db.ChatDB
import pt.ipbeja.chatapp.db.Contact
import pt.ipbeja.chatapp.db.Coordinates
import pt.ipbeja.chatapp.utils.TAG
import java.io.File
import java.nio.file.Files
import java.time.LocalDate

class CreateContactViewModel(private val app: Application) : AndroidViewModel(app) {


    private val dao = ChatDB(app).contactDao()

    private var name: String? = null
    var photoFile: File? = null

    private var date: LocalDate? = null
    private var location: Coordinates? = null

    init {
        Log.i(TAG, "CreateContactViewmodel created")
    }

    fun setName(name: String?): Boolean {
        if (name.isNullOrBlank()) return false
        this.name = name
        return true
    }


    fun setDate(date: LocalDate): Boolean {
        this.date = date
        return true
    }

    fun setLocation(lat: Double, long: Double): Boolean {
        if (lat !in -90.0..90.0 || long !in -180.0..180.0) return false
        this.location = Coordinates(lat, long)
        return true
    }

    fun createContact(): Boolean {
        if (name.isNullOrBlank() || date == null || location == null) return false

        viewModelScope.launch {
            dao.insert(Contact(name!!, date!!, location!!))
        }

        // TODO persist contact photo & reference it (path to file) in the contact table
        return true
    }




}