package com.example.helloworld.view

import android.graphics.BitmapFactory

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
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

import com.example.helloworld.R
import com.example.helloworld.ui.components.LiquidActionButton
import com.example.helloworld.ui.components.LiquidBackdropEngine
import com.example.helloworld.ui.components.LiquidGlassIconButton
import com.example.helloworld.ui.components.LiquidGlassSurface
import com.example.helloworld.ui.theme.AliceFontFamily

import kotlin.math.roundToInt

@Preview
@Composable
fun ScreenSeJuntarEtapa2(
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
                .align(Alignment.TopStart)
                .offset(
                    x = 20.dp,
                    y = 205.dp
                )
                .size(
                    width = 375.dp,
                    height = 514.dp
                )
        ) {
            LiquidGlassIconButton(
                engine = engine,
                iconRes = R.drawable.check,
                iconDescription = "check",
                onClick = {},
                size = 30.dp,
                greenCircleSize = 22.dp,
                iconSize = 15.dp,
                glassOpacity = 1.2f,
                centerFill = 0.12f,
                edgeFill = 0.15f,
                isInteractive = false,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(y = 75.dp, x = 25.dp)
            )
            Text(
                text = "Código valido",
                fontFamily = AliceFontFamily,
                color = Color(0xFF344530),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp,

                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = 65.dp, y = 80.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.instituicao),
                contentDescription = "instituicao",
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(330.dp)
                    .offset(y = -90.dp)
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
                    .size(width = 330.dp, height = 100.dp)
                    .offset(y = -45.dp)
            ) {

            }

        }
    }
}