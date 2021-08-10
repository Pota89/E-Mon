package angelini.domotica.ui.room

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import angelini.domotica.data.db.Device
import angelini.domotica.databinding.ListItemDeviceBinding

//take Device rows and adapt them for RecyclerView in RoomFragment
class DeviceAdapter : ListAdapter<Device, DeviceViewHolder>(DeviceDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        return DeviceViewHolder(
            ListItemDeviceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        val device = getItem(position)
        holder.bind(device)
    }
}

private class DeviceDiffCallback : DiffUtil.ItemCallback<Device>() {

    override fun areItemsTheSame(oldItem: Device, newItem: Device): Boolean {
        return oldItem.room.type == newItem.room.type && oldItem.room.number == newItem.room.number && oldItem.type == newItem.type && oldItem.number == newItem.number
    }

    override fun areContentsTheSame(oldItem: Device, newItem: Device): Boolean {
        return oldItem == newItem
    }
}