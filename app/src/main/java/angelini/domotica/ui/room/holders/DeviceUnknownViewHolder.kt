package angelini.domotica.ui.room.holders

import androidx.recyclerview.widget.RecyclerView
import angelini.domotica.repository.datatypes.Device
import angelini.domotica.databinding.ListItemDeviceUnknownBinding

/**
 * ViewHolder generico per Device non conosciuti
 *
 * Utilizzato quando da rete MQTT viene trovato un dispositivo non riconosciuto
 * dal programma, ad esempio un valore non riconosciuto dalla Enum Device
 */
class DeviceUnknownViewHolder(@Suppress("UNUSED_PARAMETER") val binding: ListItemDeviceUnknownBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(@Suppress("UNUSED_PARAMETER")item: Device)  {
    }
}