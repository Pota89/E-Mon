package angelini.domotica.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import angelini.domotica.R
import angelini.domotica.data.db.RoomType
import angelini.domotica.data.db.Room

//code to configure a single row of Room in HomeFragment
class RoomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val roomImage: ImageView = view.findViewById(R.id.room_image)
    val roomName: TextView = view.findViewById(R.id.room_name)

    //companion objects act like static methods of a class, here inflate the layout for a new RoomViewHolder
    companion object {
        fun from(parent: ViewGroup): RoomViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.fragment_home_room_item, parent, false)
            return RoomViewHolder(view)
        }
    }

    fun bind(item: Room)  {

        //room image
        when (item.type) {
            RoomType.BATHROOM -> roomImage.setImageResource(R.drawable.ic_room_bathroom)
            RoomType.BEDROOM -> roomImage.setImageResource(R.drawable.ic_room_bedroom)
            RoomType.KITCHEN -> roomImage.setImageResource(R.drawable.ic_room_kitchen)
            RoomType.LOUNGE -> roomImage.setImageResource(R.drawable.ic_room_lounge)
            RoomType.STUDY -> roomImage.setImageResource(R.drawable.ic_room_study)
            RoomType.GARAGE -> roomImage.setImageResource(R.drawable.ic_room_garage)
            RoomType.HALL -> roomImage.setImageResource(R.drawable.ic_room_hall)
            RoomType.DINING -> roomImage.setImageResource(R.drawable.ic_room_dining)
            RoomType.HALLWAY -> roomImage.setImageResource(R.drawable.ic_room_hallway)
            else -> roomImage.setImageResource(R.drawable.ic_room_unknown)
        }

        //room string
        val builder = StringBuilder()

        when (item.type) {
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

        if (item.number!=0){
            builder.append(" ")
            builder.append(item.number)
        }

        roomName.text=builder.toString()
    }
}