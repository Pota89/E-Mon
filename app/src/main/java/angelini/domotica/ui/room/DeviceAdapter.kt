package angelini.domotica.ui.room

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import angelini.domotica.databinding.*
import angelini.domotica.repository.datatypes.Device
import angelini.domotica.repository.datatypes.DeviceType
import angelini.domotica.ui.room.holders.*

/**
 * Adatta la visualizzazione a seconda del tipo di Device
 *
 * Ogni tipo di Device ha una sua visualizzazione personalizzata ma tutte ereditano
 * dalla classe generica View.
 * Al costruttore si passa una callback di un metodo del ViewModel
 * per notificarlo quando è necessario aggiornare l'interfaccia grafica.
 */
class DeviceAdapter(private val onUpdateCallback: (Device) -> Unit) : ListAdapter<Device, RecyclerView.ViewHolder>(DeviceDiffCallback()) {
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
                ),
                onUpdateCallback
            )

            DeviceType.SHUTTER -> return DeviceShutterViewHolder(
                ListItemDeviceShutterBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                onUpdateCallback
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

    //TODO definire caso di astrazione con ViewHolder?
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val device = getItem(position)
        when(DeviceType.values()[holder.itemViewType]){//get DeviceType enum from viewType
            DeviceType.TEMPERATURE -> (holder as DeviceTemperatureViewHolder).bind(device)
            DeviceType.MOVEMENT -> (holder as DeviceMovementViewHolder).bind(device)
            DeviceType.LAMP -> (holder as DeviceLampViewHolder).bind(device)
            DeviceType.SHUTTER -> (holder as DeviceShutterViewHolder).bind(device)
            else -> (holder as DeviceUnknownViewHolder).bind(device)
        }
    }
}

/**
 * Definisce se gli elementi di una nuova lista sono gli stessi della vecchia.
 *
 * RecyclerView quando deve fare gli aggiornamenti opera valutando le differenze tra
 * le liste, per ottimizzare il consumo di memoria ricicla le View già create cambiando
 * solo il contenuto
 */
private class DeviceDiffCallback : DiffUtil.ItemCallback<Device>() {
    /**
     * Indica se stiamo valutando lo stesso elemento ma con valori eventualmente cambiati
     */
    override fun areItemsTheSame(oldItem: Device, newItem: Device): Boolean {
        return oldItem.room.type == newItem.room.type && oldItem.room.number == newItem.room.number && oldItem.type == newItem.type && oldItem.number == newItem.number
    }

    /**
     * Indica se l'elemento valutato è uguale in tutto
     *
     * Significa che l'elemento è rimasto immutato tra la vecchia e la nuova lista a
     * disposizione di RecyclerView.
     * Sfruttando la definizione come "data class" di Device ereditiamo il metodo
     * che permette di fare uguaglianze tra oggetti
     */
    override fun areContentsTheSame(oldItem: Device, newItem: Device): Boolean {
        return oldItem == newItem
    }
}