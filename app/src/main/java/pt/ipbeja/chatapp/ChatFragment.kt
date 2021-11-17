package pt.ipbeja.chatapp

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import pt.ipbeja.chatapp.databinding.ChatFragmentBinding
import pt.ipbeja.chatapp.databinding.IncomingMessageBinding
import pt.ipbeja.chatapp.databinding.OutgoingMessageBinding
import pt.ipbeja.chatapp.db.ChatDB
import pt.ipbeja.chatapp.db.Message
import pt.ipbeja.chatapp.db.MessageDirection
import kotlin.random.Random

class ChatFragment : Fragment() {

    private val args : ChatFragmentArgs by navArgs()

    private lateinit var binding: ChatFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ChatFragmentBinding.inflate(inflater).let {
        this.binding = it
        it.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val contact = ChatDB(requireContext())
            .contactDao()
            .get(args.contactId)

        val adapter = MessageAdapter()
        /*
        val messages = List(100) {
            val direction = if(Random.nextBoolean()) MessageDirection.INCOMING else MessageDirection.OUTGOING
            Message(0, "Text message $it", direction, (it+1).toLong())
        }
        adapter.data = messages.toMutableList()
        */

        binding.chatList.adapter = adapter

        println(contact)
    }

    inner class MessageAdapter : RecyclerView.Adapter<BaseViewHolder>() {

        var data : MutableList<Message> = mutableListOf()
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
            if(viewType == 0) { // Outgoing
                val v = LayoutInflater.from(parent.context)
                    .inflate(R.layout.outgoing_message, parent, false)
                return OutgoingMessageViewHolder(v)

            }
            else {
                val v = LayoutInflater.from(parent.context)
                    .inflate(R.layout.incoming_message, parent, false)
                return IncomingMessageViewHolder(v)
            }

        }

        override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
            holder.bind(data[position])
        }

        override fun getItemViewType(position: Int): Int {
            return if(data[position].direction == MessageDirection.INCOMING) 1 else 0
        }

        override fun getItemCount() = data.size

    }


    abstract inner class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var message: Message
        abstract fun bind(message: Message)

    }

    inner class IncomingMessageViewHolder(view: View) : BaseViewHolder(view) {

        private val binding: IncomingMessageBinding = IncomingMessageBinding.bind(view)

        override fun bind(message: Message) {
            this.message = message
            binding.messageText.text = message.text
        }

    }

    inner class OutgoingMessageViewHolder(view: View) : BaseViewHolder(view) {

        private val binding: OutgoingMessageBinding = OutgoingMessageBinding.bind(view)

        override fun bind(message: Message) {
            this.message = message
            binding.messageText.text = message.text
        }

    }


}