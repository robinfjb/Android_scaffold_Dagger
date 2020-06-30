package robin.scaffold.dagger.ui.room

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_tools.*
import robin.scaffold.dagger.R
import robin.scaffold.dagger.di.Injectable
import robin.scaffold.dagger.utils.MyViewModelFactory
import robin.scaffold.dagger.viewmodel.RoomViewModel
import javax.inject.Inject

class RoomFragment : Fragment() , Injectable {
    private lateinit var toolsViewModel: RoomViewModel
    @Inject
    lateinit var factory: MyViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        toolsViewModel = ViewModelProviders.of(this, factory).get(RoomViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_tools, container, false)
        toolsViewModel.getTex().observe(viewLifecycleOwner, Observer {
            text_tools.text = it
        })
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        add.setOnClickListener {
            toolsViewModel.generateData()
        }
        delete.setOnClickListener {
            val id = delete_id.editableText.toString().toInt()
            toolsViewModel.delete(id)
        }
        query_1.setOnClickListener {
            toolsViewModel.queryAll()
        }
        query_2.setOnClickListener {
            val name = query_2_name.editableText.toString()
            val lowP = query_2_price1.editableText.toString().toInt()
            val highP = query_2_price2.editableText.toString().toInt()
            toolsViewModel.queryByFilter(name, lowP, highP)
        }
    }
}