package angelini.domotica.manual

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import angelini.domotica.databinding.FragmentHomeBinding
import angelini.domotica.dummy.DummyContent

class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val adapter = RoomAdapter()
        binding.roomList.adapter = adapter
        viewModel.rooms.observe(viewLifecycleOwner, Observer {adapter.data=it})
        /*
        sleepTrackerViewModel.nights.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })*/

        return binding.root
    }
}