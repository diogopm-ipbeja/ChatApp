package pt.ipbeja.chatapp

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.google.android.material.snackbar.Snackbar
import pt.ipbeja.chatapp.databinding.CreateContactFragmentBinding
import pt.ipbeja.chatapp.db.ChatDB
import pt.ipbeja.chatapp.db.Contact
import pt.ipbeja.chatapp.db.Coordinates
import pt.ipbeja.chatapp.utils.TAG
import java.time.LocalDate
import java.util.*

class CreateContactViewModel(app: Application) : AndroidViewModel(app) {

    private val dao = ChatDB(app).contactDao()

    private var name: String? = null
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
        dao.insert(Contact(name!!, date!!, location!!))
        return true
    }


}

class CreateContactFragment : Fragment() {

    private val viewModel: CreateContactViewModel by navGraphViewModels(R.id.createContact)

    private lateinit var binding: CreateContactFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = CreateContactFragmentBinding.inflate(inflater).let {
        this.binding = it
        it.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.createContactBtn.setOnClickListener {

            with(binding) {

                val name = nameInput.text?.toString()
                if (!viewModel.setName(name)) {
                    // do not proceed if the user didn't type the contact name
                    // we can show a pop-up warning to the user
                    val errorMessage = getString(R.string.create_contact_missing_name_error)
                    Snackbar.make(
                        requireView(),
                        errorMessage,
                        Snackbar.LENGTH_SHORT
                    ).setAction(android.R.string.ok) {/* Nothing */ }
                        .show()

                    nameInputLayout.error = errorMessage
                    return@setOnClickListener
                }


                val dateOfBirth =
                    LocalDate.of(dobPicker.year, dobPicker.month, dobPicker.dayOfMonth)
                viewModel.setDate(dateOfBirth)

                /*val contact = Contact(name, dateOfBirth)
                val id = ChatDB(requireContext())
                    .contactDao()
                    .insert(contact)*/
                Log.i(CreateContactFragment::class.java.simpleName, "Contact created with id $id")

                findNavController().navigate(
                    CreateContactFragmentDirections.actionCreateContactFragmentToContactLocation()
                )


            }


        }

    }

}