package com.example.composestart


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composestart.ui.theme.ComposeStartTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeStartTheme {
                // A surface container using the 'background' color from the theme
                //Greeting("Android")
                MessageCard(Message("story", "haha"))
                //  Conversation(SampleData.conversationSample)
            }
        }
    }
}

data class Message(val title: String, val body: String)


@Composable
fun Custom() {

  /*  Column() {
        MessageCard(Message("story", "haha"))
        Spacer(modifier = Modifier.height(5.dp))
        Greeting("Android")
    }*/
    Box(contentAlignment = Alignment.Center) {
        MyBox(modifier = Modifier.size(100.dp),color = Color.Blue)
        MyBox(modifier = Modifier.size(50.dp),color = Color.Yellow)
        MyBox(modifier = Modifier.size(20.dp),color = Color.Green)
    }


}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}


@Composable
fun MessageCard(msg: Message) {

    var isImageClicked by remember { mutableStateOf(false)}
    Surface(
        elevation = 10.dp,
        modifier = Modifier.padding(all = 10.dp)
    ) {
        Row(
            modifier = Modifier.padding(all = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.android_image),
                contentDescription = "default Image",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, Color.Green, CircleShape)
                    .clickable { isImageClicked = !isImageClicked }


            )
            Spacer(modifier = Modifier.width(8.dp))

            Surface(color = if(isImageClicked) Color.Blue else Color.Yellow) {

            Column() {
                Text(
                    text = "Message title ${msg.title}",
                    color = Color.Cyan
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Message body ${msg.body}",
                    color = Color.Blue
                )
            }
            }
        }
    }

}




@Composable
fun MyBox(modifier: Modifier = Modifier,color: Color){
    Box(modifier = modifier
        .background(color)

      ) {
    }
}

@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn() {
        items(messages) { message -> MessageCard(msg = message) }
    }
}


@Preview(
    showBackground = true,
    // uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun DefaultPreview() {
    ComposeStartTheme {
        //Greeting("Android")
        //MessageCard(Message("story", "haha"))
        // Conversation(SampleData.conversationSample)
        Custom()
    }
}