package angelini.domotica.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import angelini.domotica.repository.datatypes.Room
import angelini.domotica.databinding.ListItemRoomBinding

/**
 * Prende in ingresso una lista di Room e la "adatta" per mostrarla nella RecyclerView
 */
class RoomAdapter : ListAdapter<Room, RoomViewHolder>(RoomDiffCallback()) {
    /**
     * Genera una View da inserire nella RecyclerView
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        return RoomViewHolder(
            ListItemRoomBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    /**
     * Associa a una View un determinato item (oggetto Room)
     *
     * RecyclerView ricicla le View esistenti cambiando l'item che rappresenta
     */
    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        val room = getItem(position)
        holder.bind(room)
    }
}

/**
 * Definisce se gli elementi di una nuova lista sono gli stessi della vecchia.
 *
 * RecyclerView quando deve fare gli aggiornamenti opera valutando le differenze tra
 * le liste, per ottimizzare il consumo di memoria ricicla le View già create cambiando
 * solo il contenuto
 */
private class RoomDiffCallback : DiffUtil.ItemCallback<Room>() {

    /**
     * Indica se stiamo valutando lo stesso elemento ma con valori eventualmente cambiati
     */
    override fun areItemsTheSame(oldItem: Room, newItem: Room): Boolean {
        return oldItem.type == newItem.type && oldItem.number == newItem.number
    }

    /**
     * Indica se l'elemento valutato è uguale in tutto
     *
     * Significa che l'elemento è rimasto immutato tra la vecchia e la nuova lista a
     * disposizione di RecyclerView.
     * Sfruttando la definizione come "data class" di Room ereditiamo il metodo
     * che permette di fare uguaglianze tra oggetti
     */
    override fun areContentsTheSame(oldItem: Room, newItem: Room): Boolean {
        return oldItem == newItem
    }
}