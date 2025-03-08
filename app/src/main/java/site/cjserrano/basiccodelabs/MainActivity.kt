package site.cjserrano.basiccodelabs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import site.cjserrano.basiccodelabs.ui.theme.BasicCodelabsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BasicCodelabsTheme {
                MyApp(
                    modifier = Modifier.fillMaxSize()
                ) // this is what sets the whole activity modifier
            }
        }
    }
}

@Composable
fun MyApp(
    modifier: Modifier = Modifier
) {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }
    // In Compose, UI elements are not hidden, but rather they aren't added to the composition
    Surface(modifier) {
        if (shouldShowOnboarding) {
            OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
        } else {
            Greetings()
        }
    }
}

@Preview
@Composable
fun MyAppPreview() {
    // we add the preview for MyApp since the state will be hoisted
    BasicCodelabsTheme {
        MyApp(Modifier.fillMaxSize())
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val expanded =
        rememberSaveable { mutableStateOf(false) } // This will remember the state value for each composable
    val extraPadding =
        if (expanded.value) 48.dp else 0.dp // we add an extra padding to the composable when the button is clicked

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
                    expanded.value =
                        !expanded.value // here we change the state value of the composable, which will be recomposed and then redrawn in the UI
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

@Composable
fun Greetings(
    modifier: Modifier = Modifier,
    names: List<String> = List(1000) { "$it" }
) {
    // refactored to encapsulate  - changed to LazyColumn so it can load huge amounts of data
    // LazyColumn and LazyRow are equivalent to RecyclerView
    // It's cheaper and more efficient since it emits new composables as you scroll
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(items = names) { name ->
            Greeting(name = name)
        }
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun GreetingsPreview() {
    BasicCodelabsTheme {
        Greetings()
    }
}


// Onboarding screen
@Composable
fun OnboardingScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to the Basics Codelab!")
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinueClicked
        ) {
            Text("Continue")
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    BasicCodelabsTheme {
        // Since we have to pass a lambda to the onboarding screen for the button listener...
        // we pass an empty statement
        OnboardingScreen(onContinueClicked = {})
    }
}
