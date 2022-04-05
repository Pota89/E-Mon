package angelini.domotica.ui.home

import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import angelini.domotica.R
import angelini.domotica.repository.datatypes.Room
import angelini.domotica.repository.datatypes.RoomType
import angelini.domotica.databinding.ListItemRoomBinding

/**
 * Definisce come deve essere riempita la singola View a partire dall'item
 */
class RoomViewHolder(private val binding: ListItemRoomBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Room)  {

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

        val viewContext=binding.root.context
        when (item.type) {
            RoomType.BATHROOM -> builder.append(viewContext.getString(R.string.room_bathroom))
            RoomType.BEDROOM -> builder.append(viewContext.getString(R.string.room_bedroom))
            RoomType.KITCHEN -> builder.append(viewContext.getString(R.string.room_kitchen))
            RoomType.LOUNGE -> builder.append(viewContext.getString(R.string.room_lounge))
            RoomType.STUDY -> builder.append(viewContext.getString(R.string.room_study))
            RoomType.GARAGE -> builder.append(viewContext.getString(R.string.room_garage))
            RoomType.HALL -> builder.append(viewContext.getString(R.string.room_hall))
            RoomType.DINING -> builder.append(viewContext.getString(R.string.room_dining))
            RoomType.HALLWAY -> builder.append(viewContext.getString(R.string.room_hallway))
            else -> builder.append(viewContext.getString(R.string.room_unknown))
        }

        if (item.number!=0){
            builder.append(" ")
            builder.append(item.number)
        }

        //navigation to selected room
        binding.root.setOnClickListener {
            val direction = HomeFragmentDirections.actionNavHomeToRoomFragment()
            direction.roomType = item.type
            direction.roomNumber=item.number
            direction.roomName=builder.toString()
            binding.root.findNavController().navigate(direction)
        }

        binding.roomName.text=builder.toString()
    }
}