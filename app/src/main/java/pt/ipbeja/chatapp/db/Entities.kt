package pt.ipbeja.chatapp.db

import androidx.room.*

@Entity
data class Contact(
    val name: String,
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
)

@Entity
data class Message(
    val contactId: Long,
    val text: String,
    val direction: MessageDirection,
    @PrimaryKey(autoGenerate = true) val id: Long = 0
)

enum class MessageDirection {
    INCOMING, OUTGOING
}