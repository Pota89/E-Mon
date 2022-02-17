package angelini.domotica.ui.room

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import angelini.domotica.MainApplication
import angelini.domotica.databinding.FragmentRoomBinding
import angelini.domotica.ui.RepositoryViewModelFactory

class RoomFragment : Fragment() {
    private lateinit var viewModelFactory: RepositoryViewModelFactory
    private lateinit var viewModel: RoomViewModel

    private lateinit var binding: FragmentRoomBinding
    private val args: RoomFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModelFactory = RepositoryViewModelFactory((activity?.application as MainApplication).getRepository())
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(RoomViewModel::class.java)

        binding = FragmentRoomBinding.inflate(inflater, container, false)
        val adapter = DeviceAdapter()
        binding.deviceList.adapter = adapter

        //TODO fix the observer with Flow
        viewModel.getRoomDevices(args.roomType,args.roomNumber).observe(viewLifecycleOwner, {device ->
            adapter.submitList(device)})


        return binding.root
    }
}