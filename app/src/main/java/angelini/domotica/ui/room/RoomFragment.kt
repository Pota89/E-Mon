package angelini.domotica.ui.room

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import angelini.domotica.R
import angelini.domotica.data.db.RoomType
import angelini.domotica.databinding.FragmentHomeBinding
import angelini.domotica.databinding.FragmentRoomBinding
import angelini.domotica.ui.home.HomeViewModel
import angelini.domotica.ui.home.RoomAdapter

class RoomFragment : Fragment() {
    private val viewModel: RoomViewModel by viewModels()
    private lateinit var binding: FragmentRoomBinding
    private val args: RoomFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRoomBinding.inflate(inflater, container, false)

        val adapter = DeviceAdapter()
        binding.deviceList.adapter = adapter
        viewModel.getRoomDevices(args.roomType,args.roomNumber).observe(viewLifecycleOwner, {device ->
            adapter.submitList(device)})

        return binding.root
    }
}