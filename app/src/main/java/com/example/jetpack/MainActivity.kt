package com.example.jetpack

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.ui.theme.JetpackTheme
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackTheme() {
                Conversation(SampleData.conversationSample)
            }
        }
    }
}

data class Message(val author: String, val body: String)

@Composable
fun MessageCard(msg: Message) {
    Row (modifier = Modifier.padding(all = 8.dp)){
        Image(
            painter = painterResource(R.drawable.tima),
            contentDescription = "Contact profile picture",
            modifier = Modifier
                .size(40.dp) //размер картинки
                .clip(CircleShape) //закругление картинки
                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape) //добавил шрину обврдки и цвет
        )
        Spacer(modifier = Modifier.width(8.dp)) //промежуток по горизонатли между картинкой и текстом
        var isExpanded by remember { mutableStateOf(false) }
        val surfaceColor: Color by animateColorAsState(
            if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
        )
        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            Text(
                text = msg.author, //вывели кто написал
                color = MaterialTheme.colors.secondaryVariant, //изменили цвет текста
                style = MaterialTheme.typography.subtitle2 //изменил шрифт
            )
            Spacer(modifier = Modifier.height(4.dp)) //промежуток по вертикали между строками
            Surface(shape = MaterialTheme.shapes.medium, elevation = 1.dp,color = surfaceColor,modifier = Modifier.animateContentSize().padding(1.dp)){
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(all = 4.dp),
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}
//@Preview(
//    name = "LightMode"
//)
//@Preview(
//    uiMode = Configuration.UI_MODE_NIGHT_YES,
//    showBackground = true,
//    name = "DarkMode"
//)
//@Composable
//fun PreviewMessageCard() {
//    JetpackTheme() {
//        MessageCard(
//            Conversation(SampleData.conversationSample)
//        )
//    }
//}

@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn {
        items(messages) { message ->
            MessageCard(message)
        }
    }
}
