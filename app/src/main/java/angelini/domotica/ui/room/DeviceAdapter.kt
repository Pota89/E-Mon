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
    /**
     * Effettua l'inflate di una View per RecyclerView, differenziata per tipo
     *
     * L'idea di RecyclerView è quella di riciclare gli elementi dell'UI quando
     * si scorre la lista, le View sono differenziate per RecyclerView tramite
     * il parametro viewType
     *
     * @property parent la view parent, in questo caso il singolo Holder della RecyclerView
     * @property viewType il valore numerico utilizzato per differenziare le view generate
     */
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

    /**
     * Restituisce il tipo di Device all'interno della lista nell'Adapter
     *
     * @property position indice dell'elemento nella lista nell'Adapter
     */
    override fun getItemViewType(position: Int): Int {
        val item=getItem(position)
        return item.type.ordinal//return Int equivalent of DeviceType Enum
    }

    /**
     * In base al tipo di Device da visualizzare, recupera una Viewholder adeguata
     *
     * Se è disponibile una ViewHolder adeguata e inutilizzata nel pool
     * delle View, RecyclerView la recupera e associa i dati del Device d'interesse.
     * In assenza, RecyclerView crea in memora una ViewHolder per quel tipo di Device
     *
     * @property holder holder su cui fare il collegamento dei dati
     * @property position indice dell'elemento nella lista nell'Adapter usato da RecyclerView
     */
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