package angelini.domotica.repository.datatypes

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity

/**
 * Classe dati che definisce la struttura di un Device
 *
 * Le annotazioni servono per la generazione della tabella nel database
 * generato dalla libreria Room di Android
 */
@Entity(primaryKeys = ["roomType", "roomNumber", "deviceType", "deviceNumber"])
data class Device (
    @NonNull @Embedded val room: Room = Room(),
    @NonNull @ColumnInfo(name = "deviceType")val type: DeviceType = DeviceType.UNKNOWN,
    @NonNull @ColumnInfo(name = "deviceNumber")val number:Int=0,
    @NonNull @ColumnInfo(name = "deviceValue") var value:Int=0
)