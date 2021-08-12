package angelini.domotica.ui.room.holders

import androidx.recyclerview.widget.RecyclerView
import angelini.domotica.data.db.Device
import angelini.domotica.data.db.DeviceType
import angelini.domotica.databinding.ListItemDeviceLampBinding
import angelini.domotica.databinding.ListItemDeviceTemperatureBinding

class DeviceLampViewHolder(private val binding: ListItemDeviceLampBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Device)  {
        binding.switchLamp.isChecked = item.value!=0//return true if value is different than 0
        val builder = StringBuilder()
        builder.append("Lampada")

        if (item.number!=0){
            builder.append(" ")
            builder.append(item.number)
        }

        binding.lampName.text=builder.toString()
    }
}