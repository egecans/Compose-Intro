package com.example.jetpackcomposeintro

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import com.example.jetpackcomposeintro.ui.theme.JetPackComposeIntroTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fontFamily = FontFamily(
            Font(R.font.square_peg_regular),
            Font(R.font.rubik_distressed_regular),
        )
        setContent { // = setContentView
            //ColumnRowModifier("Hello","World")
            //ShowImage()
            //StylishText(fontFamily)
            TwoBoxes()
        }
    }


    // when clicking upper box, bottom boxes color will change
    @Composable fun TwoBoxes(modifier: Modifier = Modifier){
        val color = remember { // remember the compose from last iteration instead of initalizing it as yellow
            mutableStateOf(Color.Yellow) //livedata gibi ama valuesu değiştikçe callanmadan değişiyor
        }
        Column(Modifier.fillMaxSize()) {
            ColorBox(modifier
                .weight(1f)
                .fillMaxSize()){
                // bottom color uses this value, when we click on the upper, it will change the bottom's color
                color.value = it
            }
            Box(Modifier
                .weight(1f)
                .fillMaxSize()
                .background(color.value))
        }
    }

    // redraw UI Components -> redrawing
    @Composable fun ColorBox(modifier: Modifier = Modifier,
    updateColor: (Color) -> Unit){ // lambda function that takes color as a parameter where it is called

        Box(modifier = modifier
            .background(Color.Red)
            .clickable {
                updateColor(
                    Color(
                        Random.nextFloat(),
                        Random.nextFloat(),
                        Random.nextFloat(),
                        1f
                    )
                )
            })
    }

    @Composable fun StylishText(fontFamily: FontFamily){
        Box(modifier = Modifier
            .fillMaxSize()
            .background(
                Color(0xFF101010),
            )) {
            Text(text = buildAnnotatedString {
                                             withStyle(style = SpanStyle(
                                                 color = Color.Red,
                                                 fontSize = 40.sp
                                             )){
                                                 append("E")
                                             }
                append("gecan ")
                withStyle(style = SpanStyle(
                    color = Color.Red,
                    fontSize = 40.sp
                )){
                    append("S")
                }
                append("erbester ")
            }
                , color = Color.White, fontSize = 20.sp,
                fontFamily = fontFamily, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center
            ) // res -> fonts aç, indirdiklerini koy
        }
    }


    @Composable fun ShowImage(){
        val painter = painterResource(id = R.drawable.e46)
        ImageCard(painter = painter, description = "Description" , title = "Sanat", modifier = Modifier.fillMaxWidth(0.8f))
    }

    // composable functions started with capital letter
    @Composable
    fun ImageCard(painter: Painter,
                  description: String,
                  title: String,
                  modifier: Modifier = Modifier){
        Card(
            modifier = modifier.fillMaxWidth(),
            shape = RoundedCornerShape(15.dp)
        ){
            Box(modifier = Modifier.height(200.dp)){// column and row for normal container
                Image(painter = painter, contentDescription = description, contentScale = ContentScale.Crop)
                Box (modifier = Modifier // create a child-box at the bottom
                    .fillMaxSize()
                    .padding(8.dp),
                    contentAlignment = Alignment.BottomStart){// boxun içine konanları aşağıda göster
                            Text(text = title, style = TextStyle(color = Color.White, fontSize = 16.sp))
                    }
            }
        }

    }

    @Composable
    fun ColumnRowModifier(msg1: String, msg2: String){
        // Row yan yana ekliyor listeleri, column dikine
        // alignment (kendi ekseninde )vs arrangement (cross ekseninde)
        // main axis: Kendi tek satırınınki, rowda aynı satır, columnda aynı sütun. Itemların stacklendiği yer
        // cross axis: Birden fazla item olursa nasıl olacağı, karşıdan karşıya geçen cross
        // columnda, horizontal allignment aynı listedekileri ortalar, ama default dp olduğundan max_width olmadan gözükmez
        Column(
            modifier = Modifier
                //.fillMaxSize(0.6f)
                //.requiredWidth(600.dp) // ekrandan fazlasına da taşar
                .background(color = Color.Gray)
                .padding(5.dp) // padding en yukarıda olsa ilk onu yapacağından 8.dp boşluk olurdu backgroundda
                .width(250.dp)
                .fillMaxHeight(0.9f)
                // border paddingden sonra olduğu için aşağıda başladı
                .border(5.dp, color = Color.Magenta, shape = RectangleShape)
                // sequential çağrılabilir
                .padding(5.dp)
                .border(5.dp, color = Color.Blue, shape = RectangleShape)
                .padding(10.dp)
                .border(10.dp, color = Color.Green, shape = RectangleShape)
            //.padding(10.dp)
        )
        {
            Text(text = msg1,
                modifier = Modifier
                    .padding(10.dp)
                    .border(5.dp, color = Color.Red, shape = RectangleShape)
                    .padding(5.dp)
                    .offset(15.dp, 15.dp)
                    .border(8.dp, color = Color.Black)
                    .padding(8.dp)
                    .clickable {
                        Log.i("Hey", "Hello World")
                    }
            )
            Spacer(modifier = Modifier.height(30.dp)) // araya boşluk koyuyor
            Text(text = msg2, modifier = Modifier.padding(start = 10.dp))
        }
    }

    // define UI
    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        JetPackComposeIntroTheme {
            ColumnRowModifier("Hello","World")
            //Greeting("Android")
        }
    }

}