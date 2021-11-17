package pt.ipbeja.chatapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import pt.ipbeja.chatapp.databinding.ContactListItemBinding
import pt.ipbeja.chatapp.databinding.FragmentContactsBinding

class ContactsFragment : Fragment() {

    private val adapter: ContactsAdapter = ContactsAdapter()
    private lateinit var binding: FragmentContactsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentContactsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.contactList.adapter = adapter
        binding.addContactBtn.setOnClickListener {
            findNavController().navigate(ContactsFragmentDirections.actionContactsFragmentToCreateContactFragment())
        }
    }


    inner class ContactViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ContactListItemBinding.bind(view)

        init {

            binding.deleteBtn.setOnClickListener {
                adapter.data.removeAt(adapterPosition)
                adapter.notifyItemRemoved(adapterPosition)
            }

        }


        fun bind(contact: Contact) {
            binding.contactName.text = contact.name
        }

    }

    inner class ContactsAdapter : RecyclerView.Adapter<ContactViewHolder>() {

        var data: MutableList<Contact> = mutableListOf()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
            val v: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.contact_list_item, parent, false)
            return ContactViewHolder(v)
        }

        override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
            val contact = data[position]
            holder.bind(contact)
        }

        override fun getItemCount() = data.size

    }


}