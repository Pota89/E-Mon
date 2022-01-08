package angelini.domotica.ui.room

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import angelini.domotica.repository.datatypes.Device
import angelini.domotica.repository.datatypes.DeviceType
import angelini.domotica.databinding.ListItemDeviceUnknownBinding
import angelini.domotica.databinding.ListItemDeviceLampBinding
import angelini.domotica.databinding.ListItemDeviceMovementBinding
import angelini.domotica.databinding.ListItemDeviceTemperatureBinding
import angelini.domotica.ui.room.holders.DeviceLampViewHolder
import angelini.domotica.ui.room.holders.DeviceMovementViewHolder
import angelini.domotica.ui.room.holders.DeviceTemperatureViewHolder
import angelini.domotica.ui.room.holders.DeviceUnknownViewHolder

//take Device rows and adapt them for RecyclerView in RoomFragment
class DeviceAdapter : ListAdapter<Device, RecyclerView.ViewHolder>(DeviceDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when(DeviceType.values()[viewType])//get DeviceType enum from viewType
        {
            DeviceType.TEMPERATURE -> return DeviceTemperatureViewHolder(
                ListItemDeviceTemperatureBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            DeviceType.MOVEMENT -> return DeviceMovementViewHolder(
                ListItemDeviceMovementBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            DeviceType.LAMP -> return DeviceLampViewHolder(
                ListItemDeviceLampBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            else -> return DeviceUnknownViewHolder(
                ListItemDeviceUnknownBinding.inflate(
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
            DeviceType.TEMPERATURE -> (holder as DeviceTemperatureViewHolder).bind(device)
            DeviceType.MOVEMENT -> (holder as DeviceMovementViewHolder).bind(device)
            DeviceType.LAMP -> (holder as DeviceLampViewHolder).bind(device)
            else -> (holder as DeviceUnknownViewHolder).bind(device)
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