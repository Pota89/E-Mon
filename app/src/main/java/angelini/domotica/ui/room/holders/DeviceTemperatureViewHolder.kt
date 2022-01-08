package angelini.domotica.ui.room.holders

import androidx.recyclerview.widget.RecyclerView
import angelini.domotica.repository.datatypes.Device
import angelini.domotica.repository.datatypes.DeviceType
import angelini.domotica.databinding.ListItemDeviceTemperatureBinding

class DeviceTemperatureViewHolder(private val binding: ListItemDeviceTemperatureBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Device)  {
        val builder = StringBuilder()

        when(item.type)
        {
            DeviceType.TEMPERATURE -> builder.append("Temperatura")
            DeviceType.MOVEMENT -> builder.append("Movimento")
            DeviceType.LAMP -> builder.append("Lampada")
            else -> builder.append("Sconosciuto")
        }

        if (item.number!=0){
            builder.append(" ")
            builder.append(item.number)
        }

        binding.deviceName.text=builder.toString()
    }
}