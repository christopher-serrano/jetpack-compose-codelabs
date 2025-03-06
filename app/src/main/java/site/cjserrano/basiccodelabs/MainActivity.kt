package site.cjserrano.basiccodelabs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import site.cjserrano.basiccodelabs.ui.theme.BasicCodelabsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val names: List<String> = listOf("World", "Compose")
        setContent {
            BasicCodelabsTheme {
                MyApp(
                    modifier = Modifier.fillMaxSize(),
                    names
                ) // this is what sets the whole activity modifier
            }
        }
    }
}

@Composable
fun MyApp(
    modifier: Modifier = Modifier, names: List<String> = listOf("World", "Compose")
) {
    Column(modifier = modifier.padding(vertical = 4.dp)) {
        for (name in names) {
            Greeting(name = name)
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val expanded = remember { mutableStateOf(false) } // This will remember the state value for each composable
    val extraPadding = if (expanded.value) 48.dp else 0.dp // we add an extra padding to the composable when the button is clicked

    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding) // here we add the modifier for the composable (to be executed after clicking the button)
            ) {
                Text(text = "Hello ")
                Text(text = "${name}!")
            }
            ElevatedButton(
                onClick = {
                    expanded.value = !expanded.value // here we change the state value of the composable, which will be recomposed and then redrawn in the UI
                }
            ) {
                Text(if (expanded.value) "Show Less" else "Show More")
            }
        }
    }
}

// the preview is for showing in the layout inspector?
@Preview(showBackground = true, widthDp = 320)
@Composable
fun GreetingPreview() {
    BasicCodelabsTheme {
        MyApp()
    }
}