package mr.lalic.playground

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mr.lalic.playground.data.Epg
import mr.lalic.playground.data.Vod
import mr.lalic.playground.data.XoItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainApp(
        viewModel: MainViewModel,
        showMessage: ((String) -> Unit)? = null
) {
    Column(modifier = Modifier.padding(10.dp)) {
        Text(
                "A day wandering through the sandhills " +
                        "in Shark Fin Cove, and a few of the " +
                        "sights I saw",
                style = typography.h6,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = Color.Black

        )

        Spacer(Modifier.height(16.dp))

        val myData: List<XoItem> by viewModel.dataSet.observeAsState(emptyList())

        NextedXoListView(
                header = "Names",
                xoItems = myData,
                onItemClick = {
                    showMessage?.invoke("click")
                    if (it == null) {
                        viewModel.populateWithData()
                    }
                }
        )
    }
}

/**
 * Display a list of names the user can click with a header
 */
@ExperimentalFoundationApi
@Composable
fun NextedXoListView(
        header: String,
        xoItems: List<XoItem>,
        onItemClick: (XoItem?) -> Unit
) {
    LazyColumn {
        item {
            Text(header, color = Color.LightGray, style = typography.h5)
            Divider()
        }
        if (xoItems.isEmpty()) {
            item {
                XoItemView(xoItem = null, onClicked = { onItemClick(null) })
            }
        } else {
            val grouped = xoItems.groupBy { it.javaClass }
            grouped.forEach { (groupName, list) ->
//                stickyHeader {
//                    Text(text = "$groupName", Modifier
//                            .fillMaxWidth()
//                            .background(color = Color.LightGray))
//                }
                item {
                    HorizontalXoListView(
                            xoItems = list,
                            onItemClick = {
                                // todo
                            }
                    )
                }
//                items(list) { xoItem ->
//                    XoItemView(
//                            xoItem = xoItem,
//                            onClicked = onNameClicked
//                    )
//                }
            }

        }
    }
}

@Composable
fun HorizontalXoListView(
        xoItems: List<XoItem>,
        onItemClick: (XoItem?) -> Unit
) {
    LazyRow(horizontalArrangement = Arrangement
            .spacedBy(10.dp)) {
        items(xoItems) { xoItem ->
            XoItemView(
                    xoItem = xoItem,
                    onClicked = onItemClick
            )
        }
    }
}

/**
 * Display a single name the user can click.
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun XoItemView(xoItem: XoItem?, onClicked: (XoItem?) -> Unit) {
    val pictureResId = when (xoItem) {
        is Epg -> R.drawable.header
        is Vod -> R.drawable.vod
        else -> R.drawable.not_found
    }

    var expanded by remember { mutableStateOf(false) }

    Row(modifier = Modifier
            .padding(vertical = 8.dp)
            .focusable(true)
            .onFocusChanged {
                Log.i("BOBAN", "focus changed for ${xoItem.toString()}")
                Log.i("BOBAN", "focus state =  $it")
            }
    ) {
        Image(
                painter = painterResource(id = pictureResId),
                contentDescription = null,
                modifier = Modifier
                        .height(140.dp)
                        .width(200.dp)
                        .clip(shape = RoundedCornerShape(14.dp))
                        .clickable {
                            expanded = !expanded
                        },
                contentScale = ContentScale.Crop
        )

        AnimatedVisibility(visible = expanded) {
            Column(
                    modifier = Modifier
                            .padding(horizontal = 4.dp)
            ) {
                Text(
                        text = xoItem?.getXoName() ?: "not found",
                        style = typography.body1,
                        color = Color.Black,
                        modifier = Modifier.clickable(onClick = {
                            onClicked(
                                    xoItem
                            )
                        })
                )
                Spacer(Modifier.height(16.dp))
                Text(
                        text = "Lorem ipsum bla bla truc kandahar blandahar",
                        style = typography.body2,
                        color = Color.Black
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
//    Greeting(viewModel())
}