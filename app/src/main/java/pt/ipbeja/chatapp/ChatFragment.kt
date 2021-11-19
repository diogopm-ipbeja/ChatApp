package pt.ipbeja.chatapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
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

        val contact = ChatDB(requireContext()).contactDao().get(args.contactId)

        (requireActivity() as AppCompatActivity).supportActionBar?.title = contact.name

        // Obter a lista de mensagens do contacto
        val messages = ChatDB(requireContext())
            .messageDao()
            .getAll(args.contactId)


        val adapter = MessageAdapter()
        adapter.data = messages.toMutableList()

        (binding.chatList.layoutManager as LinearLayoutManager).let {
            it.stackFromEnd = true
        }

        binding.sendMsgBtn.setOnClickListener {
            val text = binding.messageInput.text?.toString()
            if(!text.isNullOrBlank()) { // Garantir que há uma mensagem a enviar

                // Dar uma direcção aleatória para simular a conversa
                val direction = if(Random.nextBoolean()) MessageDirection.INCOMING else MessageDirection.OUTGOING

                // Criamos uma mensagem com os dados e o id a 0 (default)
                val msg = Message(args.contactId, text, direction)

                // Ao inserir na BD, devolve-nos o id auto-gerado (Long)
                val id = ChatDB(requireContext())
                    .messageDao()
                    .insert(msg)

                // Em vez de colocarmos a mensagem que criamos com o id a 0, podemos fazer uma cópia
                // da mensagem apenas alterando o id para o id auto-gerado acima
                // (feature reservada às data classes)

                val message = msg.copy(id = id)
                adapter.addMessage(message)

                // depois de inserir, vamos fazer scroll até ao item inserido
                binding.chatList.smoothScrollToPosition(adapter.data.lastIndex)

                // por fim limpamos a caixa de texto para a próxima mensagem
                binding.messageInput.setText("", TextView.BufferType.EDITABLE)
            }
        }

        binding.chatList.adapter = adapter
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

        fun addMessage(message: Message) {
            this.data.add(message)
            notifyItemInserted(data.size-1)
        }

    }


    abstract inner class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var message: Message

        //abstract fun bind(message: Message) ou uma função 'aberta' (pode ser overriden)
        open fun bind(message: Message) {
            this.message = message
        }

    }

    inner class IncomingMessageViewHolder(view: View) : BaseViewHolder(view) {

        private val binding: IncomingMessageBinding = IncomingMessageBinding.bind(view)

        override fun bind(message: Message) {
            super.bind(message)
            binding.messageText.text = message.text
        }
    }

    inner class OutgoingMessageViewHolder(view: View) : BaseViewHolder(view) {

        private val binding: OutgoingMessageBinding = OutgoingMessageBinding.bind(view)

        override fun bind(message: Message) {
            super.bind(message)
            binding.messageText.text = message.text
        }

    }


}