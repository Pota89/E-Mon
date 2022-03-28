package angelini.domotica.repository.datatypes

import androidx.annotation.NonNull
import androidx.room.ColumnInfo

/**
 * Classe dati che definisce la struttura di una Room
 *
 * Le annotazioni servono per la generazione della tabella nel database
 * generato dalla libreria Room di Android
 */
data class Room (
    @NonNull @ColumnInfo(name = "roomType")val type: RoomType = RoomType.UNKNOWN,
    @NonNull @ColumnInfo(name = "roomNumber")val number:Int=0,
)