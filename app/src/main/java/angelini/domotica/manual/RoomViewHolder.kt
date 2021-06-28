package angelini.domotica.manual

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import angelini.domotica.R
import angelini.domotica.data.db.DeviceType
import angelini.domotica.data.db.RoomType
import angelini.domotica.data.db.Device

//code to configure a single row of Room in HomeFragment
class RoomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val contentView: TextView = view.findViewById(R.id.content)

    //companion objects act like static methods of a class, here inflate the layout for a new RoomViewHolder
    companion object {
        fun from(parent: ViewGroup): RoomViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.fragment_home_room_item, parent, false)
            return RoomViewHolder(view)
        }
    }

    fun bind(item: Device)  {
        val builder = StringBuilder()

        when (item.room.type) {
            RoomType.BATHROOM -> builder.append("Bagno")
            RoomType.BEDROOM -> builder.append("Camera")
            RoomType.KITCHEN -> builder.append("Cucina")
            RoomType.LOUNGE -> builder.append("Soggiorno")
            RoomType.STUDY -> builder.append("Studio")
            RoomType.GARAGE -> builder.append("Garage")
            RoomType.HALL -> builder.append("Ingresso")
            RoomType.DINING -> builder.append("Sala da pranzo")
            RoomType.HALLWAY -> builder.append("Corridoio")
            else -> builder.append("Sconosciuto")
        }

        if (item.room.number!=0){
            builder.append(" ")
            builder.append(item.room.number)
        }

        builder.append(" ")
        when (item.type) {
            DeviceType.TEMPERATURE -> builder.append("Temperatura")
            DeviceType.LAMP -> builder.append("Lampada")
            DeviceType.MOVEMENT -> builder.append("Movimento")
            else -> builder.append("Sconosciuto")
        }

        if (item.number!=0){
            builder.append(" ")
            builder.append(item.number)
        }

        builder.append(" ")
        if (item.value==0)
            builder.append("--")
        else
            builder.append((item.value))

        contentView.text=builder.toString()
    }
}