package pt.ipbeja.chatapp.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.util.*

@Entity
data class Contact(
    val name: String,
    val dateOfBirth: LocalDate,
    val createdAt: Date = Date(),
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
)

@Entity
data class Message(
    val contactId: Long,
    val text: String,
    val direction: MessageDirection,
    val createdAt: Date = Date(),
    @PrimaryKey(autoGenerate = true) val id: Long = 0
)

enum class MessageDirection {
    INCOMING, OUTGOING
}