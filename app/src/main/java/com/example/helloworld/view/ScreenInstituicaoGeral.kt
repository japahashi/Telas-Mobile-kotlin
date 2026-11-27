package com.example.helloworld.view

import android.annotation.SuppressLint
import android.graphics.BitmapFactory

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
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
fun ScreenInstituicaoGeral(
    modifier: Modifier = Modifier,
    onNavigateToConversa: () -> Unit = {}
){

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

        // Card Liquid Glass principal
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

            // BOX INTERNO COM SCROLL
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState())
            ) {

                Image(
                    painter = painterResource(id = R.drawable.instituicao),
                    contentDescription = "instituicao",
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .size(330.dp)
                        .offset(y = -120.dp)
                        .graphicsLayer {
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
                        .size(width = 330.dp, height = 110.dp)
                        .offset(y = -110.dp)
                ) {
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
                            .size(width = 35.dp, height = 25.dp)
                            .offset(y = -35.dp, x = 50.dp)

                    ){}

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
                        text = "Escola técnica",
                        fontFamily = AliceFontFamily,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF5C6754),
                        fontSize = 12.sp,
                        modifier = Modifier
                            .offset(x = 100.dp, y = 32.dp)
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
                        .size(width = 65.dp, height = 65.dp)
                        .offset(x = -115.dp, y = -160.dp)
                ) {

                    Image(
                        painterResource(id = R.drawable.senai),
                        contentDescription = "logo_senai",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(52.dp)
                    )
                }
            }
        }
    }
}