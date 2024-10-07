package com.msktask.ui.oldxml.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.msktask.R
import com.msktask.ui.oldxml.viewmodel.EventsAndResultsXMLViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventsFragment : Fragment(R.layout.fragment_events_and_results) {

    private var eventAdapter: EventsAdapter? = null
    private val viewModel: EventsAndResultsXMLViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        initViewModel()
        viewModel.loadEvents()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(view)
        initRecycleView(view)
    }

    private fun initRecycleView(view: View) {
        eventAdapter = EventsAdapter(object : OnEventClickListener {
            override fun onEventClick(eventId: String) {
                viewModel.selectedEvent(eventId)
            }
        })

        view.findViewById<RecyclerView>(R.id.events_and_results_recycle_view).apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = eventAdapter
        }
    }

    private fun initToolbar(view: View) {
        val toolbar = view.findViewById<Toolbar>(R.id.events_and_results_toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_back_button)
        toolbar.setNavigationOnClickListener {
            requireActivity().finish()
        }
        toolbar.findViewById<TextView>(R.id.events_and_results_toolbar_title).text = getString(R.string.event_list_header)
    }

    private fun initViewModel() {
        viewModel.eventViewState.observe(viewLifecycleOwner) { result ->
            when (result) {
                is EventsAndResultsXMLViewModel.EventViewState.Error -> {
                    Toast.makeText(requireContext(), getString(R.string.error, result.errorCode), Toast.LENGTH_SHORT).show()
                }

                is EventsAndResultsXMLViewModel.EventViewState.Success -> {
                    eventAdapter?.setData(result.data)
                }
            }
        }
    }
}