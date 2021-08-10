package angelini.domotica.ui.room.holders

import androidx.recyclerview.widget.RecyclerView
import angelini.domotica.data.db.Device
import angelini.domotica.data.db.DeviceType
import angelini.domotica.databinding.ListItemDeviceLampBinding
import angelini.domotica.databinding.ListItemDeviceTemperatureBinding

class DeviceLampViewHolder(private val binding: ListItemDeviceLampBinding) : RecyclerView.ViewHolder(binding.root) {

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