package com.msktask.ui.jetpackcompose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.msktask.R
import com.msktask.ui.model.MSKDataUI
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventListView(
    isLoading: Boolean,
    eventList: List<MSKDataUI>,
    onEventClicked: (String) -> Unit,
    onBackClicked: () -> Unit
) {
    val localContext = LocalContext.current
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.event_list_header),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        onBackClicked()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.width(64.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            }
        } else {

            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
            ) {
                items(eventList) { item ->

                    var validityText by remember {
                        mutableStateOf(localContext.getString(R.string.ends_never_validity))
                    }

                    var textColor by remember {
                        mutableIntStateOf(R.color.black)
                    }

                    var isClickable by remember {
                        mutableStateOf(true)
                    }

                    item.validity?.let {
                        var tick by remember {
                            mutableIntStateOf(item.validity)
                        }
                        LaunchedEffect(Unit) {
                            while (tick > 0) {
                                delay(1.seconds)
                                tick -= 1
                                validityText = localContext.getString(R.string.ends_in_secs_validity, tick)
                            }
                            if (tick == 0) {
                                validityText = localContext.getString(R.string.ended_validity)
                                textColor = R.color.red
                                isClickable = false
                            }
                        }
                    }

                    Column(
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clickable {
                                if (isClickable) {
                                    onEventClicked(item.id)
                                }
                            }
                    ) {

                        //id
                        Text(
                            text = stringResource(id = R.string.event_id, item.id),
                            modifier = Modifier
                                .wrapContentSize(),
                            fontSize = 22.sp,
                            color = colorResource(id = textColor),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        //description
                        Text(
                            text = stringResource(id = R.string.description, item.desc),
                            modifier = Modifier
                                .wrapContentSize(),
                            fontSize = 14.sp,
                            color = colorResource(id = textColor),
                            maxLines = 4,
                            overflow = TextOverflow.Ellipsis
                        )

                        //validity
                        Text(
                            text = validityText,
                            modifier = Modifier
                                .wrapContentSize(),
                            fontSize = 14.sp,
                            color = colorResource(id = textColor),
                            maxLines = 4,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}
