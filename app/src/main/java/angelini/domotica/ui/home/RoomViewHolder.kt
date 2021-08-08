package angelini.domotica.ui.home

import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import angelini.domotica.R
import angelini.domotica.data.db.Room
import angelini.domotica.data.db.RoomType
import angelini.domotica.databinding.ListItemRoomBinding

//code to configure a single row of Room in HomeFragment

class RoomViewHolder(private val binding: ListItemRoomBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Room)  {

        //navigation to selected room
        binding.root.setOnClickListener {
            val direction = HomeFragmentDirections.actionNavHomeToRoomFragment()
            direction.roomType = item.type
            direction.roomNumber=item.number
            binding.root.findNavController().navigate(direction)
        }

        //room image
        when (item.type) {
            RoomType.BATHROOM -> binding.roomImage.setImageResource(R.drawable.ic_room_bathroom)
            RoomType.BEDROOM -> binding.roomImage.setImageResource(R.drawable.ic_room_bedroom)
            RoomType.KITCHEN -> binding.roomImage.setImageResource(R.drawable.ic_room_kitchen)
            RoomType.LOUNGE -> binding.roomImage.setImageResource(R.drawable.ic_room_lounge)
            RoomType.STUDY -> binding.roomImage.setImageResource(R.drawable.ic_room_study)
            RoomType.GARAGE -> binding.roomImage.setImageResource(R.drawable.ic_room_garage)
            RoomType.HALL -> binding.roomImage.setImageResource(R.drawable.ic_room_hall)
            RoomType.DINING -> binding.roomImage.setImageResource(R.drawable.ic_room_dining)
            RoomType.HALLWAY -> binding.roomImage.setImageResource(R.drawable.ic_room_hallway)
            else -> binding.roomImage.setImageResource(R.drawable.ic_room_unknown)
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

        binding.roomName.text=builder.toString()
    }
}