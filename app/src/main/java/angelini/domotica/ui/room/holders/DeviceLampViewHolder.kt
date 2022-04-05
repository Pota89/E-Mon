package angelini.domotica.ui.room.holders

import androidx.recyclerview.widget.RecyclerView
import angelini.domotica.repository.datatypes.Device
import angelini.domotica.databinding.ListItemDeviceLampBinding

/**
 * ViewHolder per il Device Lamp
 */
class DeviceLampViewHolder(private val binding: ListItemDeviceLampBinding , private val onUpdateCallback: (Device) -> Unit) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Device)  {
        binding.switchLamp.setOnClickListener {
            if(binding.switchLamp.isChecked)
                item.value=1
            else
                item.value=0

            onUpdateCallback(item)
        }
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