package pt.ipbeja.chatapp

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import pt.ipbeja.chatapp.databinding.CreateContactFragmentBinding

// TODO Completar este Fragment.
//  Definir um layout que permita ao utilizador indicar o nome do utilizador
//  Opcionalmente, pode definir também outros atributos (fotografia, data de nascimento, etc.)
//  -- O contacto aqui criado deve ser guardado na base de dados
class CreateContactFragment : Fragment() {


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
    }

}