package com.msktask.ui.oldxml.results

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.msktask.R
import com.msktask.ui.oldxml.viewmodel.EventsAndResultsXMLViewModel

class ResultsFragment : Fragment(R.layout.fragment_events_and_results) {

    private var resultAdapter: ResultsAdapter? = null
    private val viewModel: EventsAndResultsXMLViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        initViewModel()
        val selectedEvent = arguments?.getString(SELECTED_EVENT_ID_EXTRA)
        selectedEvent?.let {
            viewModel.getResultsForEvent(selectedEvent)
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun initViewModel() {
        viewModel.resultsList.observe(viewLifecycleOwner) {
            resultAdapter?.setData(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(view)
        initRecycleView(view)
    }

    private fun initRecycleView(view: View) {

        resultAdapter = ResultsAdapter()
        resultAdapter?.onResultClick = {
        }
        view.findViewById<RecyclerView>(R.id.events_and_results_recycle_view).apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = resultAdapter
        }
    }

    private fun initToolbar(view: View) {
        val toolbar = view.findViewById<Toolbar>(R.id.events_and_results_toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_back_button)
        toolbar.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStackImmediate()
        }
        toolbar.findViewById<TextView>(R.id.events_and_results_toolbar_title).text = getString(R.string.result_list_header)
    }

    companion object {
        const val SELECTED_EVENT_ID_EXTRA = "selected_event_id"
    }
}