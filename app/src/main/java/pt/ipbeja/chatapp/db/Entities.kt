package pt.ipbeja.chatapp.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.OffsetDateTime

@Entity
data class Contact(
    val name: String,
    val dateOfBirth: LocalDate,
    val createdAt: OffsetDateTime = OffsetDateTime.now(),
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
)

@Entity
data class Message(
    val contactId: Long,
    val text: String,
    val direction: MessageDirection,
    val createdAt: OffsetDateTime = OffsetDateTime.now(),
    @PrimaryKey(autoGenerate = true) val id: Long = 0
)

enum class MessageDirection {
    INCOMING, OUTGOING
}