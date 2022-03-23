package angelini.domotica.ui.room.holders

import android.util.Log
import android.widget.SeekBar
import androidx.recyclerview.widget.RecyclerView
import angelini.domotica.repository.datatypes.Device
import angelini.domotica.databinding.ListItemDeviceShutterBinding

class DeviceShutterViewHolder(private val binding: ListItemDeviceShutterBinding, private val onUpdateCallback: (Device) -> Unit) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Device)  {
        binding.seekBarShutter.progress=item.value

        binding.seekBarShutter.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                item.value=seekBar!!.progress
                onUpdateCallback(item)
            }

        })

        val builder = StringBuilder()
        builder.append("Serranda")

        if (item.number!=0){
            builder.append(" ")
            builder.append(item.number)
        }

        binding.shutterName.text=builder.toString()
    }
}