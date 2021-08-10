package angelini.domotica.ui.room

import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import angelini.domotica.R
import angelini.domotica.data.db.Device
import angelini.domotica.data.db.DeviceType
import angelini.domotica.data.db.Room
import angelini.domotica.data.db.RoomType
import angelini.domotica.databinding.ListItemDeviceBinding
import angelini.domotica.databinding.ListItemRoomBinding

//code to configure a single Device in HomeFragment

class DeviceViewHolder(private val binding: ListItemDeviceBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Device)  {
        val builder = StringBuilder()

        when(item.type)
        {
            DeviceType.TEMPERATURE -> builder.append("Temperatura")
            DeviceType.MOVEMENT -> builder.append("Temperatura")
            DeviceType.LAMP -> builder.append("Temperatura")
            else -> builder.append("Sconosciuto")
        }

        if (item.number!=0){
            builder.append(" ")
            builder.append(item.number)
        }

        binding.deviceName.text=builder.toString()
    }
}