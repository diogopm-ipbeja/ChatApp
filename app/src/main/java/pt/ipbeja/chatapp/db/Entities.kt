package pt.ipbeja.chatapp.db

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.OffsetDateTime


@Entity
data class Contact(
    val name: String,
    val dateOfBirth: LocalDate,
    @Embedded val location: Coordinates,
    val createdAt: OffsetDateTime = OffsetDateTime.now(),
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
)

data class Coordinates(val latitude: Double, val longitude: Double)

@Entity(
    foreignKeys = [ForeignKey(
        entity = Contact::class,
        parentColumns = ["id"],
        childColumns = ["contactId"],
        onDelete = ForeignKey.CASCADE
    )]
)
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