package pt.ipbeja.chatapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import pt.ipbeja.chatapp.databinding.ContactLocationFragmentBinding
import pt.ipbeja.chatapp.db.ChatDB
import pt.ipbeja.chatapp.db.Contact
import pt.ipbeja.chatapp.db.Coordinates

class ContactLocation : Fragment() {

    private val args: ContactLocationArgs by navArgs()
    private lateinit var binding: ContactLocationFragmentBinding

    private var marker: Marker? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ContactLocationFragmentBinding.inflate(inflater).let {
        this.binding = it
        it.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync { map ->

            map.setOnMapClickListener {
                if (marker == null) {
                    marker = map.addMarker(MarkerOptions().position(it))
                } else {
                    marker!!.position = it
                }
            }
        }

        binding.createContactBtn.setOnClickListener {


            if (marker == null) {
                Snackbar.make(
                    requireView(),
                    "You must set the contact location.",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
            else {
                val contact = Contact(name = args.contactName, dateOfBirth = args.contactDateOfBirth, Coordinates(marker!!.position.latitude, marker!!.position.longitude))
                ChatDB(requireContext())
                    .contactDao()
                    .insert(contact)

                findNavController().popBackStack(R.id.contactsFragment, false)
            }
        }
    }

}