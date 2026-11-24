package com.example.helloworld.view

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.helloworld.R
import com.example.helloworld.ui.components.LiquidBackdropEngine
import com.example.helloworld.ui.components.LiquidGlassCircle
import com.example.helloworld.ui.components.LiquidGlassIconButton
import com.example.helloworld.ui.components.LiquidGlassSurface
import com.example.helloworld.ui.theme.AliceFontFamily
import kotlin.math.roundToInt

@SuppressLint("UnusedBoxWithConstraintsScope")
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ScreenChatbotHistorico(
    modifier: Modifier = Modifier,
    onNavigateToConversa: () -> Unit = {}
) {

    val context = LocalContext.current
    val density = LocalDensity.current

    // Carrega a imagem de fundo
    val backgroundBitmap = remember {
        BitmapFactory.decodeResource(
            context.resources,
            R.drawable.forest_background
        )
    }

    BoxWithConstraints(
        modifier = modifier.fillMaxSize()
    ) {

        // Calcula o tamanho da tela
        val screenW = with(density) {
            maxWidth.roundToPx().coerceAtLeast(1)
        }

        val screenH = with(density) {
            maxHeight.roundToPx().coerceAtLeast(1)
        }

        // Cria o motor Liquid Glass
        val engine = remember(screenW, screenH, backgroundBitmap) {
            LiquidBackdropEngine(
                screenWidth = screenW,
                screenHeight = screenH,
                density = density.density,
                sourcePhoto = backgroundBitmap
            )
        }

        // Imagem de fundo
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawImage(
                image = engine.fullResBackground,
                dstSize = IntSize(
                    size.width.roundToInt(),
                    size.height.roundToInt()
                )
            )
        }

        // Card Liquid Glass
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

                // Círculo Liquid Glass com círculo verde dentro

                LiquidGlassCircle(
                    engine = engine,
                    onClick = {},
                    size = 45.dp,
                    greenCircleSize = 32.dp,
                    greenColor = Color(0xFF435F3F),
                    glassOpacity = 1f,
                    centerFill = 0.12f,
                    edgeFill = 0.15f,
                    isInteractive = true,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .offset(x = (-10).dp, y = 0.dp)
                        .zIndex(1f)
                )

                // Texto ao lado do círculo
                Text(
                    text = "Meus Bloooms",
                    fontFamily = AliceFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 19.sp,
                    color = Color(0xFF344530),
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .offset(x = 45.dp, y = 8.dp)
                        .zIndex(2f)
                )

// PRIMEIRO CARD
                LiquidGlassSurface(
                    engine = engine,
                    cornerRadius = 20.dp,
                    refractionHeight = 22.dp,
                    refractionAmount = 22.dp,
                    centerFill = 0.3f,
                    edgeFill = 0.15f,
                    glassOpacity = 0.8f,
                    isInteractive = true,
                    onClick = onNavigateToConversa,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .offset(x = 0.dp, y = 90.dp)
                        .size(360.dp, 52.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {

                        Text(
                            text = "Porque me sinto preocupado?",
                            fontFamily = AliceFontFamily,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 19.sp,
                            color = Color(0xFF344530),
                            modifier = Modifier
                                .align(Alignment.Center)
                                .offset(x = (-10).dp)
                        )

                        LiquidGlassIconButton(
                            engine = engine,
                            iconRes = R.drawable.trash,
                            iconDescription = "Lixo",
                            onClick = {},
                            size = 27.dp,
                            greenCircleSize = 17.dp,
                            iconSize = 13.dp,
                            greenColor = Color(0xFF435F3F),
                            glassOpacity = 1.1f,
                            centerFill = 0.12f,
                            edgeFill = 0.15f,
                            isInteractive = true,
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .offset(x = (-10).dp)
                                .zIndex(1f)
                        )
                    }
                }

// SEGUNDO CARD (AGORA É IRMÃO DO PRIMEIRO)
                LiquidGlassSurface(
                    engine = engine,
                    cornerRadius = 20.dp,
                    refractionHeight = 22.dp,
                    refractionAmount = 22.dp,
                    centerFill = 0.3f,
                    edgeFill = 0.15f,
                    glassOpacity = 0.8f,
                    isInteractive = true,
                    onClick = onNavigateToConversa,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .offset(x = 0.dp, y = 160.dp)
                        .size(360.dp, 52.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {

                        Text(
                            text = "Qual o sentido da vida?",
                            fontFamily = AliceFontFamily,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 19.sp,
                            color = Color(0xFF344530),
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .offset(x = 27.dp)
                        )

                        LiquidGlassIconButton(
                            engine = engine,
                            iconRes = R.drawable.trash,
                            iconDescription = "Lixo",
                            onClick = {},
                            size = 27.dp,
                            greenCircleSize = 17.dp,
                            iconSize = 13.dp,
                            greenColor = Color(0xFF435F3F),
                            glassOpacity = 1.1f,
                            centerFill = 0.12f,
                            edgeFill = 0.15f,
                            isInteractive = true,
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .offset(x = (-10).dp)
                                .zIndex(1f)
                        )
                    }
                }
                LiquidGlassSurface(
                    engine = engine,
                    cornerRadius = 20.dp,
                    refractionHeight = 22.dp,
                    refractionAmount = 22.dp,
                    centerFill = 0.3f,
                    edgeFill = 0.15f,
                    glassOpacity = 0.8f,
                    isInteractive = true,
                    onClick = onNavigateToConversa,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .offset(x = 0.dp, y = 230.dp)
                        .size(360.dp, 52.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {

                        Text(
                            text = "É normal a visão clarear?",
                            fontFamily = AliceFontFamily,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 19.sp,
                            color = Color(0xFF344530),
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .offset(x = 27.dp)
                        )

                        LiquidGlassIconButton(
                            engine = engine,
                            iconRes = R.drawable.trash,
                            iconDescription = "Lixo",
                            onClick = {},
                            size = 27.dp,
                            greenCircleSize = 17.dp,
                            iconSize = 13.dp,
                            greenColor = Color(0xFF435F3F),
                            glassOpacity = 1.1f,
                            centerFill = 0.12f,
                            edgeFill = 0.15f,
                            isInteractive = true,
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .offset(x = (-10).dp)
                                .zIndex(1f)
                        )
                    }
                }
                LiquidGlassSurface(
                    engine = engine,
                    cornerRadius = 20.dp,
                    refractionHeight = 22.dp,
                    refractionAmount = 22.dp,
                    centerFill = 0.3f,
                    edgeFill = 0.15f,
                    glassOpacity = 0.8f,
                    isInteractive = true,
                    onClick = onNavigateToConversa,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .offset(x = 0.dp, y = 300.dp)
                        .size(360.dp, 52.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {

                        Text(
                            text = "Dor no lado esquerdo do om...",
                            fontFamily = AliceFontFamily,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 19.sp,
                            color = Color(0xFF344530),
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .offset(x = 27.dp)
                        )

                        LiquidGlassIconButton(
                            engine = engine,
                            iconRes = R.drawable.trash,
                            iconDescription = "Lixo",
                            onClick = {},
                            size = 27.dp,
                            greenCircleSize = 17.dp,
                            iconSize = 13.dp,
                            greenColor = Color(0xFF435F3F),
                            glassOpacity = 1.1f,
                            centerFill = 0.12f,
                            edgeFill = 0.15f,
                            isInteractive = true,
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .offset(x = (-10).dp)
                                .zIndex(1f)
                        )
                    }
                }
                // BOTÃO VERDE NO FINAL DO CARD
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(bottom = 15.dp)
                        .width(120.dp)
                        .height(50.dp)
                        .offset(y = 14.dp, x = -10.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(Color(0xFF435F3F))
                        .clickable(onClick = onNavigateToConversa),
                    contentAlignment = Alignment.Center
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.add),
                        contentDescription = "Adicionar",
                        modifier = Modifier
                            .size(20.dp)
                            .align(Alignment.CenterStart)
                            .offset(x = 15.dp)
                    )
                    Text(
                        text = "Bloom",
                        fontFamily = AliceFontFamily,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 19.sp,
                        color = Color.White,
                        modifier = Modifier
                            .offset(x = 9.dp)
                    )
                }
            }
        }
    }
}
