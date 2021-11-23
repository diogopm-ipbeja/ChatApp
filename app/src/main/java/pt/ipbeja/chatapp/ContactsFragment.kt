package pt.ipbeja.chatapp

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import pt.ipbeja.chatapp.databinding.ContactListItemBinding
import pt.ipbeja.chatapp.databinding.FragmentContactsBinding
import pt.ipbeja.chatapp.db.ChatDB
import pt.ipbeja.chatapp.db.Contact

class ContactsFragment : Fragment() {

    private val adapter: ContactsAdapter = ContactsAdapter()
    private lateinit var binding: FragmentContactsBinding

    // TODO Add a menu to this Fragment with:
    //  - A button to delete ALL contacts (with user confirmation)

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
    override fun onResume() {
        super.onResume()

        val contacts = ChatDB(requireContext())
            .contactDao()
            .getAll()

        adapter.data = contacts.toMutableList()
        adapter.notifyDataSetChanged()
    }


    inner class ContactViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private lateinit var contact: Contact
        private val binding = ContactListItemBinding.bind(view)

        init {

            binding.root.setOnClickListener {
                findNavController()
                    .navigate(ContactsFragmentDirections.actionContactsFragmentToChatFragment(
                        contact.id
                    ))
            }

            binding.deleteBtn.setOnClickListener {
                // TODO Delete this contact. Show an AlertDialog for user confirmation
            }

        }

        fun bind(contact: Contact) {
            this.contact = contact
            binding.contactName.text = contact.name
            binding.contactDob.text = resources.getString(R.string.contact_item_date_of_birth, contact.dateOfBirth)
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