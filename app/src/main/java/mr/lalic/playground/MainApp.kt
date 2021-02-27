package mr.lalic.playground

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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

        XoListView(
            header = "Names",
            xoItems = myData,
            onNameClicked = {
                showMessage?.invoke(it)
                if (it == "empty") {
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
fun XoListView(
    header: String,
    xoItems: List<XoItem>,
    onNameClicked: (String) -> Unit
) {
    LazyColumn {
        item {
            Text(header, color = Color.LightGray, style = typography.h5)
            Divider()
        }
        if (xoItems.isEmpty()) {
            item {
                XoItemView(xoItem = null, onClicked = { onNameClicked("empty") })
            }
        } else {
            val grouped = xoItems.groupBy { it.javaClass }
            grouped.forEach { (groupName, list) ->
                stickyHeader {
                    Text(text = "$groupName", Modifier
                        .fillMaxWidth()
                        .background(color = Color.LightGray))
                }
                items(list) { xoItem ->
                    XoItemView(
                        xoItem = xoItem,
                        onClicked = onNameClicked
                    )
                }
            }

        }
    }
}

/**
 * Display a single name the user can click.
 */
@Composable
private fun XoItemView(xoItem: XoItem?, onClicked: (String) -> Unit) {
    val pictureResId = when (xoItem) {
        is Epg -> R.drawable.header
        is Vod -> R.drawable.vod
        else -> R.drawable.not_found
    }

    Row(
        modifier = Modifier
            .padding(vertical = 8.dp)
    ) {
        Image(
            painter = painterResource(id = pictureResId),
            contentDescription = null,
            modifier = Modifier
                .height(140.dp)
                .width(200.dp)
                .clip(shape = RoundedCornerShape(14.dp)),
            contentScale = ContentScale.Crop
        )

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
                        xoItem?.getXoName() ?: "not found"
                    )
                })
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = "Lorem ipsum bla bla truc kandahar blandahar",
                style = typography.body2,
                color = Color.Black,
                modifier = Modifier
                    .clickable(onClick = {
                        onClicked(
                            xoItem?.getXoName() ?: "not found"
                        )
                    })
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
//    Greeting(viewModel())
}