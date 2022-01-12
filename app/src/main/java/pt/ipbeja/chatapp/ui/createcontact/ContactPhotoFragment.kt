package pt.ipbeja.chatapp.ui.createcontact

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.PictureResult
import pt.ipbeja.chatapp.R
import pt.ipbeja.chatapp.ui.main.ContactsViewModel
import pt.ipbeja.chatapp.databinding.ContactPhotoFragmentBinding
import java.io.File

class ContactPhotoFragment : Fragment() {


    private lateinit var binding: ContactPhotoFragmentBinding
    private val viewModel: CreateContactViewModel by navGraphViewModels(R.id.createContact)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ContactPhotoFragmentBinding.inflate(inflater).let {
        this.binding = it
        it.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.camera.setLifecycleOwner(viewLifecycleOwner)
        binding.shutterBtn.setOnClickListener {
            binding.camera.takePicture()
        }

        binding.camera.addCameraListener(object : CameraListener() {
            override fun onPictureTaken(result: PictureResult) {
                val dir = requireContext().filesDir
                val file = File(dir, "temp.jpg")

                result.toFile(file) {
                    viewModel.photoFile = it
                    findNavController().popBackStack()
                }

            }
        })
    }

}