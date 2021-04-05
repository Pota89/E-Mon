package angelini.domotica.manual

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import angelini.domotica.R
import angelini.domotica.data.Room
import angelini.domotica.data.RoomType

class RoomAdapter: RecyclerView.Adapter<RoomAdapter.RoomViewHolder>() {

    var data =  listOf<Room>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_home_room_item, parent, false)
        return RoomViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        val item = data[position]
        when (item.roomType) {
            RoomType.KITCHEN -> holder.contentView.text = "Cucina"
            RoomType.BATHROOM -> holder.contentView.text = "Bagno"
            RoomType.LOUNGE -> holder.contentView.text = "Soggiorno"
            else -> holder.contentView.text = "Sconosciuto"
        }
    }

    override fun getItemCount(): Int = data.size

    inner class RoomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val contentView: TextView = view.findViewById(R.id.content)
    }


}