package angelini.domotica.ui.room.holders

import androidx.recyclerview.widget.RecyclerView
import angelini.domotica.R
import angelini.domotica.repository.datatypes.Device
import angelini.domotica.repository.datatypes.DeviceType
import angelini.domotica.databinding.ListItemDeviceMovementBinding

/**
 * ViewHolder per il Device Movement
 */
class DeviceMovementViewHolder(private val binding: ListItemDeviceMovementBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Device)  {
        if(item.value==0)
            binding.movementImage.setImageResource(R.drawable.ic_sensor_movement_undetected)
        else
            binding.movementImage.setImageResource(R.drawable.ic_sensor_movement_detected)

        val builder = StringBuilder()
        builder.append(binding.root.context.getString(R.string.device_movement))

        if (item.number!=0){
            builder.append(" ")
            builder.append(item.number)
        }

        binding.movementSensorName.text=builder.toString()
    }
}