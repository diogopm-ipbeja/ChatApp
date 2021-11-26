package pt.ipbeja.chatapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import pt.ipbeja.chatapp.databinding.FragmentContactsMapBinding
import pt.ipbeja.chatapp.db.ChatDB
import pt.ipbeja.chatapp.db.Contact

class ContactsMapFragment : Fragment() {

    private lateinit var binding: FragmentContactsMapBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentContactsMapBinding.inflate(inflater).let {
        this.binding = it
        it.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync { map ->
            val contacts = ChatDB(requireContext())
                .contactDao()
                .getAll()

            val boundsBuilder = LatLngBounds.builder()

            contacts.forEach {
                val latLng = LatLng(it.location.latitude, it.location.longitude)
                boundsBuilder.include(latLng)
                val m = map.addMarker(MarkerOptions()
                    .title(it.name)
                    .snippet(it.dateOfBirth.toString())
                    .position(latLng))!!
                m.tag = it
            }
            val bounds = boundsBuilder.build()

            map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 200))

            map.setOnInfoWindowClickListener {
                val c = it.tag as Contact
                findNavController().navigate(
                    ContactsMapFragmentDirections.actionContactsMapFragmentToChatFragment(
                        c.id
                    )
                )
            }
        }
    }
}