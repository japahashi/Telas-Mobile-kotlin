package com.example.helloworld.view

import android.graphics.BitmapFactory

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex

import com.example.helloworld.R
import com.example.helloworld.ui.components.LiquidActionButton
import com.example.helloworld.ui.components.LiquidBackdropEngine
import com.example.helloworld.ui.components.LiquidGlassIconButton
import com.example.helloworld.ui.components.LiquidGlassSurface
import com.example.helloworld.ui.theme.AliceFontFamily

import kotlin.math.roundToInt

@Preview
@Composable
fun ScreenSeJuntarEtapa3(
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    val context = LocalContext.current

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

        // Pega o tamanho da tela em pixels
        val screenW = with(density) {
            maxWidth.roundToPx().coerceAtLeast(1)
        }

        val screenH = with(density) {
            maxHeight.roundToPx().coerceAtLeast(1)
        }

        // Cria o motor responsável pelo Liquid Glass
        val engine = remember(screenW, screenH, backgroundBitmap) {
            LiquidBackdropEngine(
                screenWidth = screenW,
                screenHeight = screenH,
                density = density.density,
                sourcePhoto = backgroundBitmap
            )
        }

        // =================================================
        // PLANO DE FUNDO
        // =================================================

        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            drawImage(
                image = engine.fullResBackground,
                dstSize = IntSize(
                    size.width.roundToInt(),
                    size.height.roundToInt()
                )
            )
        }

        // =================================================
        // CARD GRANDE DE LIQUID GLASS
        // =================================================

        LiquidGlassSurface(
            engine = engine,
            cornerRadius = 24.dp,
            refractionHeight = 22.dp,
            refractionAmount = 22.dp,
            edgeFill = 0.20f,
            isInteractive = false,
            modifier = Modifier
                .align(Alignment.Center)
                .size(
                    width = 375.dp,
                    height = 450.dp
                )
        ) {
            LiquidGlassSurface(
                engine = engine,
                cornerRadius = 24.dp,
                refractionHeight = 22.dp,
                refractionAmount = 22.dp,
                edgeFill = 0.20f,
                glassOpacity = 1f,
                centerFill = 0.6f,
                isInteractive = false,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .size(width = 90.dp, height = 35.dp)
                    .offset(x = 20.dp, y = 20.dp)

            ){
                LiquidGlassIconButton(
                    engine = engine,
                    iconRes = R.drawable.check,
                    iconDescription = "check",
                    onClick = {},
                    size = 23.dp,
                    greenCircleSize = 15.dp,
                    iconSize = 10.dp,
                    glassOpacity = 1.2f,
                    centerFill = 0.12f,
                    edgeFill = 0.15f,
                    isInteractive = false,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .offset(x = 5.dp)

                )
                Text(
                    text = "Código",
                    fontFamily = AliceFontFamily,
                    color = Color(0xFF344530),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 10.sp,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .offset(x = 5.dp)
                )
            }
            LiquidGlassSurface(
                engine = engine,
                cornerRadius = 24.dp,
                refractionHeight = 22.dp,
                refractionAmount = 22.dp,
                edgeFill = 0.20f,
                glassOpacity = 0.9f,
                centerFill = 0.6f,
                isInteractive = false,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .size(width = 110.dp, height = 35.dp)
                    .offset(x = 125.dp, y = 20.dp)

            ){
                LiquidGlassIconButton(
                    engine = engine,
                    iconRes = R.drawable.two,
                    iconDescription = "dois",
                    onClick = {},
                    size = 23.dp,
                    greenCircleSize = 15.dp,
                    iconSize = 7.dp,
                    glassOpacity = 1.2f,
                    centerFill = 0.12f,
                    edgeFill = 0.15f,
                    isInteractive = false,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .offset(x = 5.dp)

                )
                Text(
                    text = "Instituição",
                    fontFamily = AliceFontFamily,
                    color = Color(0xFF344530),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 10.sp,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .offset(x = 5.dp)
                )
            }
            LiquidGlassSurface(
                engine = engine,
                cornerRadius = 24.dp,
                refractionHeight = 22.dp,
                refractionAmount = 22.dp,
                edgeFill = 0.20f,
                glassOpacity = 0.9f,
                centerFill = 0.6f,
                isInteractive = false,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .size(width = 110.dp, height = 35.dp)
                    .offset(x = 245.dp, y = 20.dp)

            ){
                LiquidGlassIconButton(
                    engine = engine,
                    iconRes = R.drawable.three,
                    iconDescription = "tres",
                    onClick = {},
                    size = 23.dp,
                    greenCircleSize = 15.dp,
                    iconSize = 7.dp,
                    glassOpacity = 1.2f,
                    centerFill = 0.12f,
                    edgeFill = 0.15f,
                    isInteractive = false,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .offset(x = 5.dp)


                )
                Text(
                    text = "Confirmar",
                    fontFamily = AliceFontFamily,
                    color = Color(0xFF344530),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 10.sp,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .offset(x = 5.dp)
                )
            }
            LiquidGlassIconButton(
                engine = engine,
                iconRes = R.drawable.protecao,
                iconDescription = "escudo",
                onClick = {},
                size = 52.dp,
                greenCircleSize = 35.dp,
                iconSize = 25.dp,
                glassOpacity = 1.2f,
                centerFill = 0.12f,
                edgeFill = 0.15f,
                isInteractive = false,
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(y = -110.dp)

            )
            Text(
                text = "Confirme sua entrada",
                fontFamily = AliceFontFamily,
                color = Color(0xFF344530),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp,
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(y = -60.dp)
            )
            Text(
                text = "Você está prestes a se juntar a instituição",
                fontFamily = AliceFontFamily,
                color = Color(0xFF5C6754),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 14.sp,
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(y = -33.dp)
            )
            Text(
                text = "SENAI Jandira",
                fontFamily = AliceFontFamily,
                color = Color(0xFF344530),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 13.sp,
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(y = -13.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.instituicao),
                contentDescription = "instituicao",
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(330.dp)
                    .offset(y = 48.dp)
                    .graphicsLayer{
                        alpha = 0.8f
                    }

            )

            LiquidGlassSurface(
                engine = engine,
                cornerRadius = 24.dp,
                refractionHeight = 22.dp,
                refractionAmount = 22.dp,
                edgeFill = 0.20f,
                glassOpacity = 1.2f,
                centerFill = 0.5f,
                isInteractive = false,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(width = 330.dp, height = 90.dp)
                    .offset(y = 90.dp)
            ) {
                Text(
                    text = "SENAI Jandira",
                    fontFamily = AliceFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 15.sp,
                    color = Color(0xFF344530),
                    modifier = Modifier
                        .offset(x = 100.dp, y = 10.dp)

                )
                Text(
                    text = "Escola técnica • Jandira/SP",
                    fontFamily = AliceFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF5C6754),
                    fontSize = 12.sp,
                    modifier = Modifier
                        .offset(x = 100.dp, y = 32.dp)

                )
                Text(
                    text = "6.767",
                    fontFamily = AliceFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF344530),
                    fontSize = 12.sp,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .offset(x = -25.dp, y = 19.dp)

                )
                Text(
                    text = "Integrantes",
                    fontFamily = AliceFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF5C6754),
                    fontSize = 11.sp,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .offset(x = 23.dp, y = 19.dp)

                )
                LiquidGlassIconButton(
                    engine = engine,
                    iconRes = R.drawable.check,
                    iconDescription = "check",
                    onClick = {},
                    size = 14.dp,
                    greenCircleSize = 10.dp,
                    iconSize = 6.7.dp,
                    glassOpacity = 1.9f,
                    centerFill = 0.12f,
                    edgeFill = 0.15f,
                    isInteractive = false,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = -115.dp, y = 12.dp)
                )
                LiquidGlassIconButton(
                    engine = engine,
                    iconRes = R.drawable.crowd,
                    iconDescription = "multidão",
                    onClick = {},
                    size = 21.dp,
                    greenCircleSize = 13.dp,
                    iconSize = 8.dp,
                    glassOpacity = 1.7f,
                    centerFill = 0.12f,
                    edgeFill = 0.15f,
                    isInteractive = false,
                    modifier = Modifier
                        .align (Alignment.CenterStart)
                        .offset(100.dp, y = 20.dp)

                )
            }

            LiquidGlassSurface(
                engine = engine,
                cornerRadius = 60.dp,
                refractionHeight = 22.dp,
                refractionAmount = 22.dp,
                edgeFill = 0.20f,
                glassOpacity = 1.2f,
                centerFill = 0.5f,
                isInteractive = false,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(width = 60.dp, height = 60.dp)
                    .offset(y = 55.dp, x = -115.dp)

            ){
                Image(
                    painterResource(id = R.drawable.senai),
                    contentDescription = "logo_senai",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(45.dp)
                )
            }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(bottom = 15.dp)
                    .width(335.dp)
                    .height(50.dp)
                    .offset(y = 5.dp, x = 20.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(Color(0xFF435F3F)),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "Se juntar",
                    fontFamily = AliceFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFFF7FBF2),
                    fontSize = 20.sp,
                )
                Image(
                    painterResource(R.drawable.arrow_right_white),
                    contentDescription = "seta para direita branca",
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(30.dp)
                        .offset(x = -10.dp)
                )
            }
        }
        LiquidGlassSurface(
            engine = engine,
            cornerRadius = 28.dp,
            refractionHeight = 22.dp,
            refractionAmount = 22.dp,
            edgeFill = 0.20f,
            isInteractive = false,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .size(375.dp, 145.dp)
                .offset(y = -30.dp)

        ) {


            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 18.dp
                    ),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                LiquidGlassIconButton(
                    engine = engine,
                    iconRes = R.drawable.protecao,
                    iconDescription = "Proteção",
                    size = 52.dp,
                    greenCircleSize = 40.dp,
                    iconSize = 25.dp,
                    glassOpacity = 1.6f,
                    centerFill = 0.12f,
                    edgeFill = 0.15f,
                    isInteractive = false
                )

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    Text(
                        text = "Sua privacidade continua sendo sua",
                        fontSize = 16.sp,
                        lineHeight = 22.sp,
                        fontFamily = AliceFontFamily,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF344530)
                    )

                    Text(
                        text = "Ao entrar uma instituição, apenas informações gerais da comunidade são compartilhadas.",
                        fontSize = 15.sp,
                        lineHeight = 21.sp,
                        fontFamily = AliceFontFamily,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF606C58)
                    )
                }
            }

        }
    }
}

