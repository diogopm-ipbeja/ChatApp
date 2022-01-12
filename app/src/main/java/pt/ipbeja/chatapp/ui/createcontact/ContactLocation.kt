package pt.ipbeja.chatapp.ui.createcontact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import pt.ipbeja.chatapp.R
import pt.ipbeja.chatapp.databinding.ContactLocationFragmentBinding

class ContactLocation : Fragment() {

    private val viewModel: CreateContactViewModel by navGraphViewModels(R.id.createContact)
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
                viewModel.setLocation(it.latitude, it.longitude)
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
            else if(viewModel.createContact()) {
                findNavController().popBackStack(R.id.contactsFragment, false)
            }
        }
    }

}