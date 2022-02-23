package angelini.domotica.ui.room.holders

import androidx.recyclerview.widget.RecyclerView
import angelini.domotica.repository.datatypes.Device
import angelini.domotica.databinding.ListItemDeviceTemperatureBinding

class DeviceTemperatureViewHolder(private val binding: ListItemDeviceTemperatureBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Device)  {
        val builder = StringBuilder()
        builder.append("Temperatura")

        if (item.number!=0){
            builder.append(" ")
            builder.append(item.number)
        }

        builder.append(" : ")
        builder.append(item.value)
        builder.append("°C")
        binding.temperatureSensorName.text=builder.toString()
    }
}