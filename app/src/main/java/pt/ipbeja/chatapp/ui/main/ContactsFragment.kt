package pt.ipbeja.chatapp.ui.main

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import pt.ipbeja.chatapp.R
import pt.ipbeja.chatapp.databinding.ContactListItemBinding
import pt.ipbeja.chatapp.databinding.FragmentContactsBinding
import pt.ipbeja.chatapp.db.ChatDB
import pt.ipbeja.chatapp.db.Contact
import java.io.File


class ContactsFragment : Fragment() {

    private val viewModel : ContactsViewModel by navGraphViewModels(R.id.contacts)

    private val adapter: ContactsAdapter = ContactsAdapter()
    private lateinit var binding: FragmentContactsBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentContactsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        /*
        viewModel.contactsLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        */

        lifecycleScope.launch {
            /*viewModel.contactsFlow
                .collect {
                adapter.submitList(it)
            }*/
        }


        binding.contactList.adapter = adapter
        binding.addContactBtn.setOnClickListener {
            findNavController().navigate(ContactsFragmentDirections.actionContactsFragmentToCreateContactFragment())
        }
    }
    override fun onResume() {
        super.onResume()


        //refreshList()
    }

    /*private fun refreshList() {
        *//*
        val contacts = ChatDB(requireContext())
            .contactDao()
            .getAll()
        *//*
        viewModel.refreshContacts()

        adapter.data = viewModel.contacts.toMutableList()
        adapter.notifyDataSetChanged()
    }*/

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.contacts, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.deleteAllContacts) {

            AlertDialog.Builder(requireContext())
                .setTitle("Delete all contacts")
                .setMessage("Are you sure you want to delete all contacts?")
                .setPositiveButton("Delete") { _, _ ->

                    ChatDB(requireContext())
                        .contactDao()
                        .deleteAll()

                    //refreshList()

                }
                .setNegativeButton("Cancel", null)
                .show()

            return true
        }
        else if(item.itemId == R.id.contactsMap) {
            findNavController().navigate(ContactsFragmentDirections.actionContactsFragmentToContactsMapFragment())
            return true
        }
        return super.onOptionsItemSelected(item)
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
                val msg = resources.getString(R.string.delete_contact_dialog_message, contact.name)
                AlertDialog.Builder(requireContext())
                    .setTitle(R.string.delete_contact_dialog_title)
                    .setMessage(msg)
                    .setPositiveButton(R.string.delete_contact_dialog_positive) { _, _ ->
                        // Apagar da BD, Adapter e notificar
                        ChatDB(requireContext())
                            .contactDao()
                            .delete(contact)

                        //adapter.data.remove(contact)
                        //adapter.notifyItemRemoved(adapterPosition)
                    }
                    .setNegativeButton(R.string.delete_contact_dialog_negative, null)
                    .show()
            }

        }

        fun bind(contact: Contact) {
            this.contact = contact
            binding.contactName.text = contact.name
            binding.contactDob.text = resources.getString(R.string.contact_item_date_of_birth, contact.dateOfBirth)
        }

    }


    val differ = object : DiffUtil.ItemCallback<Contact>() {
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Contact, newItem: Contact) = oldItem == newItem
    }

    inner class ContactsAdapter : ListAdapter<Contact, ContactViewHolder>(differ) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
            val v: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.contact_list_item, parent, false)
            return ContactViewHolder(v)
        }

        override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
            val contact = getItem(position)
            holder.bind(contact)
        }


    }




}