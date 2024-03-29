package pt.ipbeja.chatapp.ui.createcontact

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import coil.load
import com.google.android.material.snackbar.Snackbar
import pt.ipbeja.chatapp.R
import pt.ipbeja.chatapp.databinding.CreateContactFragmentBinding
import java.time.LocalDate
import java.util.*

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

        with(binding.photo) {
            setOnClickListener {
                findNavController().navigate(CreateContactFragmentDirections.actionCreateContactFragmentToContactPhotoFragment())
            }
            viewModel.photoFile?.let { load(it) }
        }



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
                    LocalDate.of(dobPicker.year, dobPicker.month + 1, dobPicker.dayOfMonth)
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