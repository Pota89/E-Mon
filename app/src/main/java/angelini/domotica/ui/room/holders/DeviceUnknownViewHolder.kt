package angelini.domotica.ui.room.holders

import androidx.recyclerview.widget.RecyclerView
import angelini.domotica.data.db.Device
import angelini.domotica.databinding.ListItemDeviceUnknownBinding

//code to configure a single Device in HomeFragment

class DeviceUnknownViewHolder(private val binding: ListItemDeviceUnknownBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Device)  {
    }
}