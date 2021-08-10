package angelini.domotica.ui.room

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import angelini.domotica.data.db.Device
import angelini.domotica.data.db.DeviceType
import angelini.domotica.databinding.ListItemDeviceBinding

//take Device rows and adapt them for RecyclerView in RoomFragment
class DeviceAdapter : ListAdapter<Device, RecyclerView.ViewHolder>(DeviceDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when(DeviceType.values()[viewType])//get DeviceType enum from viewType
        {
            DeviceType.TEMPERATURE -> return DeviceViewHolder(
                ListItemDeviceBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            DeviceType.MOVEMENT -> return DeviceViewHolder(
                ListItemDeviceBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            DeviceType.LAMP -> return DeviceViewHolder(
                ListItemDeviceBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            else -> return DeviceViewHolder(
                ListItemDeviceBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

        }
    }

    override fun getItemViewType(position: Int): Int {
        val item=getItem(position)
        return item.type.ordinal//return Int equivalent of DeviceType Enum
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val device = getItem(position)
        when(DeviceType.values()[holder.itemViewType]){//get DeviceType enum from viewType
            DeviceType.TEMPERATURE -> (holder as DeviceViewHolder).bind(device)
            DeviceType.MOVEMENT -> (holder as DeviceViewHolder).bind(device)
            DeviceType.LAMP -> (holder as DeviceViewHolder).bind(device)
            else -> (holder as DeviceViewHolder).bind(device)
        }
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