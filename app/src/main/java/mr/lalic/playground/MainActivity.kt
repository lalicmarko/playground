package mr.lalic.playground

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : AppCompatActivity() {

    val fullList = arrayListOf(
        "Marko",
        "suzana",
        "Leonora",
        "Danijela",
        "Andrea",
        "Radovan",
        "Ruza",
        "Jovana"
    )
    var dataSet = fullList


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var myData = dataSet
            Greeting(
                dataSet = myData,
                showMessage = {
                    Toast.makeText(this, "$it", Toast.LENGTH_SHORT).show()
                    myData[3] = "hohoho"
                },
                populateWithData = {
                    dataSet.addAll(fullList)
                }
            )
        }
    }
}

@Composable
fun getData(): List<String> {
    return arrayListOf(
        "Marko",
        "suzana",
        "Leonora",
        "Danijela",
        "Andrea",
        "Radovan",
        "Ruza",
        "Jovana"
    )
}

@Composable
fun Greeting(
    dataSet: List<String>,
    showMessage: ((String) -> Unit)? = null,
    populateWithData: (() -> Unit)? = null
) {
    Column(modifier = Modifier.padding(10.dp)) {

        Image(
            painter = painterResource(id = R.drawable.header),
            contentDescription = null,
            modifier = Modifier
                .height(280.dp)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(14.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(Modifier.height(16.dp))

        Text(
            "A day wandering through the sandhills " +
                    "in Shark Fin Cove, and a few of the " +
                    "sights I saw",
            style = typography.h6,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            color = Color.White

        )
        Text(
            "Davenport, California",
            style = typography.body2,
            color = Color.LightGray
        )
        Text(
            "December 2018",
            style = typography.body2,
            color = Color.LightGray
        )

        Spacer(Modifier.height(16.dp))

        NamePicker(
            header = "Names",
            names = dataSet,
            onNameClicked = {
                showMessage?.invoke(it)
                if (it == "empty") {
                    populateWithData?.invoke()

                }
            }
        )
    }
}

/**
 * Display a list of names the user can click with a header
 */
@Composable
fun NamePicker(
    header: String,
    names: List<String>,
    onNameClicked: (String) -> Unit
) {
    LazyColumn {
        item {
            Text(header, color = Color.LightGray, style = typography.h5)
            Divider()
        }
        if (names.isEmpty()) {
            item {
                NameItem(name = "EMPTY", onClicked = { onNameClicked("empty") })
            }
        } else {
            items(names) { name ->
                NameItem(name = name, onClicked = onNameClicked)
            }
        }

//      alternative way
//        item {
//            NameList(names = names, onClicked = { onNameClicked })
//        }
    }
}

@Composable
private fun NameList(names: List<String>, onClicked: (String) -> Unit) {
    Column {
        if (names.isEmpty()) {
            Text(
                "Empty", style = typography.h1, color = Color.Red, modifier = Modifier
                    .clickable(onClick = { onClicked("empty") })
            )
        } else {
            names.forEach { name ->
                NameItem(name, onClicked)
                Divider()
            }
        }
    }
}

/**
 * Display a single name the user can click.
 */
@Composable
private fun NameItem(name: String, onClicked: (String) -> Unit) {
    Text(
        text = name,
        style = typography.h3,
        color = Color.White,
        modifier = Modifier.clickable(onClick = { onClicked(name) })
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Greeting(emptyList())
}