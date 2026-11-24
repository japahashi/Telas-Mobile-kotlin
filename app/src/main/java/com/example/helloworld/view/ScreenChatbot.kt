package com.example.helloworld.view

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.helloworld.R
import com.example.helloworld.ui.components.LiquidActionButton
import com.example.helloworld.ui.components.LiquidBackdropEngine
import com.example.helloworld.ui.components.LiquidGlassIconButton
import com.example.helloworld.ui.components.LiquidGlassSurface
import com.example.helloworld.ui.theme.AliceFontFamily
import kotlin.math.roundToInt

@SuppressLint("UnusedBoxWithConstraintsScope")
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ScreenChatbot(
    modifier: Modifier = Modifier,
    onNavigateToConversa: () -> Unit = {}
) {

    var textoDigitado by remember { mutableStateOf("") }

    val density = LocalDensity.current
    val context = LocalContext.current

    val backgroundBitmap = remember {
        BitmapFactory.decodeResource(
            context.resources,
            R.drawable.forest_background
        )
    }

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {

        val screenW = with(density) {
            maxWidth.roundToPx().coerceAtLeast(1)
        }

        val screenH = with(density) {
            maxHeight.roundToPx().coerceAtLeast(1)
        }

        val engine = remember(screenW, screenH, backgroundBitmap) {
            LiquidBackdropEngine(
                screenWidth = screenW,
                screenHeight = screenH,
                density = density.density,
                sourcePhoto = backgroundBitmap
            )
        }

        // Plano de fundo
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawImage(
                image = engine.fullResBackground,
                dstSize = IntSize(
                    size.width.roundToInt(),
                    size.height.roundToInt()
                )
            )
        }

        // ====================================================================
        // CARD PRINCIPAL COM LIQUID GLASS
        // ====================================================================

        LiquidGlassSurface(
            engine = engine,
            cornerRadius = 24.dp,
            refractionHeight = 22.dp,
            refractionAmount = 22.dp,
            edgeFill = 0.20f,
            isInteractive = false,
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(x = 20.dp, y = 205.dp)
                .size(375.dp, 514.dp)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {

                Text(
                    text = "Olá, Manoel",
                    fontSize = 30.sp,
                    fontFamily = AliceFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .offset(y = 15.dp)
                )

                Text(
                    text = "Vamos conversar?",
                    color = Color(0xFF5C6754),
                    fontSize = 18.sp,
                    fontFamily = AliceFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .offset(y = 55.dp)
                )

                Image(
                    painter = painterResource(id = R.drawable.happy_capybara),
                    contentDescription = "logo capivara",
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .offset(y = 75.dp)
                        .size(160.dp)
                )
            }
        }

        // ====================================================================
        // 4 BOTÕES COM LIQUID GLASS
        // ====================================================================

        LiquidActionButton(
            engine = engine,
            text = "Quero refletir",
            iconRes = R.drawable.cloud,
            iconDescription = "Nuvem",
            textOffsetX = 9.dp,
            onClick = onNavigateToConversa,
            glassOpacity = 1.3f,
            modifier = Modifier
                .align(Alignment.Center)
                .offset(x = (-80).dp, y = 60.dp)
                .size(145.dp, 60.dp)
        )

        LiquidActionButton(
            engine = engine,
            text = "Meu dia",
            iconRes = R.drawable.sun,
            iconDescription = "Sol",
            onClick = onNavigateToConversa,
            glassOpacity = 1.3f,
            modifier = Modifier
                .align(Alignment.Center)
                .offset(x = 85.dp, y = 60.dp)
                .size(145.dp, 60.dp)
        )

        LiquidActionButton(
            engine = engine,
            text = "Desabafar",
            iconRes = R.drawable.leaf,
            iconDescription = "Folha",
            onClick = onNavigateToConversa,
            glassOpacity = 1.3f,
            modifier = Modifier
                .align(Alignment.Center)
                .offset(x = (-80).dp, y = 135.dp)
                .size(150.dp, 60.dp)
        )

        LiquidActionButton(
            engine = engine,
            text = "Só na\nconversa",
            iconRes = R.drawable.heart,
            iconDescription = "Coração",
            onClick = onNavigateToConversa,
            glassOpacity = 1.3f,
            modifier = Modifier
                .align(Alignment.Center)
                .offset(x = 85.dp, y = 135.dp)
                .size(145.dp, 60.dp)
        )

        // ====================================================================
        // CAMPO DE TEXTO COM LIQUID GLASS
        // ====================================================================

        LiquidGlassSurface(
            engine = engine,
            cornerRadius = 50.dp,
            refractionHeight = 14.dp,
            refractionAmount = 14.dp,
            isInteractive = false,
            glassOpacity = 0.7f,
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = 220.dp, x = (-30).dp)
                .size(240.dp, 51.7.dp)
        ) {

            OutlinedTextField(
                value = textoDigitado,
                onValueChange = { textoDigitado = it },
                placeholder = {
                    Text(
                        text = "Escreva uma mensagem",
                        fontFamily = AliceFontFamily,
                        fontSize = 14.5.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF2C3E35).copy(alpha = 0.7f)
                    )
                },

                singleLine = true,
                leadingIcon = {
                    LiquidGlassIconButton(
                        engine = engine,
                        iconRes = R.drawable.leaf,
                        iconDescription = "Folha",
                        onClick = { /* Ação do ícone, se necessário */ },
                        size = 32.dp,
                        greenCircleSize = 23.dp,
                        iconSize = 15.dp,
                        glassOpacity = 1.6f,
                        centerFill = 0.12f,
                        edgeFill = 0.15f,
                        isInteractive = false
                    )
                },
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(50.dp),

                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF435F3F),
                    unfocusedBorderColor = Color.White.copy(alpha = 0f),
                    focusedTextColor = Color(0xFF2C3E35),
                    unfocusedTextColor = Color(0xFF2C3E35),
                    unfocusedContainerColor = Color.White.copy(alpha = 0.45f),
                    focusedContainerColor = Color.White.copy(alpha = 0.45f)
                )
            )
        }

        // ====================================================================
        // BOTÃO DE ENVIAR
        // ====================================================================

        LiquidGlassIconButton(
            engine = engine,
            iconRes = R.drawable.paper_plane,
            iconDescription = "Avião de papel",
            onClick = {
                onNavigateToConversa()
            },
            size = 45.dp,
            greenCircleSize = 33.dp,
            iconSize = 20.dp,
            glassOpacity = 1.2f,
            centerFill = 0.12f,
            edgeFill = 0.15f,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = (-66).dp, y = (-182).dp)
        )

        // ====================================================================
        // AVISO INFERIOR
        // ====================================================================

        Text(
            text = "Esta é uma conversa com um chatbot controlado\n              por ia e não com uma pessoa real.",
            color = Color(0xFF344530),
            fontSize = 12.sp,
            lineHeight = 15.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = AliceFontFamily,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = (-140).dp)
        )
    }
}

// ============================================================================
