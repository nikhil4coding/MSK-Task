package com.msktask.ui.oldxml

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.msktask.R
import com.msktask.ui.oldxml.events.EventsFragment
import com.msktask.ui.oldxml.results.ResultsFragment
import com.msktask.ui.oldxml.results.ResultsFragment.Companion.SELECTED_EVENT_ID_EXTRA
import com.msktask.ui.oldxml.viewmodel.EventsAndResultsXMLViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventsAndResultsXMLActivity : AppCompatActivity(R.layout.activity_events_and_results_xml) {

    private val viewModel: EventsAndResultsXMLViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<EventsFragment>(R.id.fragment_container_view)
        }
    }

    private fun initViewModel() {
        viewModel.selectedEventId.observe(this) { eventId ->
            val args = bundleOf(SELECTED_EVENT_ID_EXTRA to eventId)
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<ResultsFragment>(R.id.fragment_container_view, args = args)
                addToBackStack(null)
            }
        }
    }

    companion object {
        fun getInstance(activity: Activity): Intent {
            val intent = Intent(activity, EventsAndResultsXMLActivity::class.java)
            return intent
        }
    }
}