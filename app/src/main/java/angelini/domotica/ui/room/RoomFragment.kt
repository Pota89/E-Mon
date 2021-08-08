package angelini.domotica.ui.room

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import angelini.domotica.R
import angelini.domotica.data.db.RoomType
import angelini.domotica.databinding.FragmentHomeBinding
import angelini.domotica.databinding.FragmentRoomBinding
import angelini.domotica.ui.home.RoomAdapter

class RoomFragment : Fragment() {
    private lateinit var binding: FragmentRoomBinding
    private val args: RoomFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRoomBinding.inflate(inflater, container, false)
        binding.testText.text="${args.roomType} ${args.roomNumber}"
        return binding.root
    }
}