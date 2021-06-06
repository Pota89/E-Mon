package angelini.domotica.manual

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import angelini.domotica.data.db.Device

//take Room rows and adapt them for RecyclerView in HomeFragment
class RoomAdapter: RecyclerView.Adapter<RoomViewHolder>() {

    var data =  listOf<Device>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        return RoomViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = data.size
}