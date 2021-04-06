package angelini.domotica.manual

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import angelini.domotica.R
import angelini.domotica.data.Room
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

    fun bind(item: Room)  {
        when (item.roomType) {
            RoomType.KITCHEN -> contentView.text = "Cucina"
            RoomType.BATHROOM -> contentView.text = "Bagno"
            RoomType.LOUNGE -> contentView.text = "Soggiorno"
            else -> contentView.text = "Sconosciuto"
        }
    }
}