package angelini.domotica.manual

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import angelini.domotica.R
import angelini.domotica.data.db.Device
import angelini.domotica.data.RoomType

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
        when (item.roomType) {
            RoomType.BATHROOM -> contentView.text = "Bagno"
            RoomType.BEDROOM -> contentView.text = "Camera"
            RoomType.KITCHEN -> contentView.text = "Cucina"
            RoomType.LOUNGE -> contentView.text = "Soggiorno"
            RoomType.STUDY -> contentView.text = "Studio"
            RoomType.GARAGE -> contentView.text = "Garage"
            RoomType.HALL -> contentView.text = "Ingresso"
            RoomType.DINING -> contentView.text = "Sala da pranzo"
            RoomType.HALLWAY -> contentView.text = "Corridoio"
            else -> contentView.text = "Sconosciuto"
        }

        if (item.roomNumber!=0){
            val tempString=contentView.text.toString()
            val outString=tempString.plus(" ").plus(item.roomNumber)
            contentView.text=outString
        }
    }
}