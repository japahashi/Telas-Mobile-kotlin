package com.example.helloworld.view

// Importa as ferramentas para carregar a imagem
import android.annotation.SuppressLint
import android.graphics.BitmapFactory

// Importa o Canvas para desenhar na tela
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box

// Importa a caixa que permite descobrir o tamanho da tela
import androidx.compose.foundation.layout.BoxWithConstraints

// Importa os modificadores de layout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size

// Importa o Composable e o remember
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

// Importa alinhamento e Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

// Importa o contexto e a densidade da tela
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview

// Importa os tipos de tamanho e unidade DP
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp


// Importa as classes do seu projeto
import com.example.helloworld.LiquidBackdropEngine
import com.example.helloworld.LiquidGlassSurface
import com.example.helloworld.R

// Importa a função de arredondamento
import kotlin.math.roundToInt


// Define a tela ThirdScreen
@Preview
@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun ThirdScreen(modifier: Modifier = Modifier) {

    // Pega o contexto do aplicativo
    val context = LocalContext.current

    // Pega a densidade da tela
    val density = LocalDensity.current

    // Carrega a imagem de fundo
    val backgroundBitmap = remember {
        BitmapFactory.decodeResource(
            context.resources,
            R.drawable.forest_background
        )
    }

    // Cria uma caixa que sabe o tamanho disponível da tela
    BoxWithConstraints(
        modifier = modifier.fillMaxSize()
    ) {

        // Converte a largura da tela de DP para pixels
        val screenW = with(density) {
            maxWidth.roundToPx().coerceAtLeast(1)
        }

        // Converte a altura da tela de DP para pixels
        val screenH = with(density) {
            maxHeight.roundToPx().coerceAtLeast(1)
        }

        // Cria o motor Liquid Glass
        val engine = remember(
            screenW,
            screenH,
            backgroundBitmap
        ) {
            LiquidBackdropEngine(
                screenWidth = screenW,
                screenHeight = screenH,
                density = density.density,
                sourcePhoto = backgroundBitmap
            )
        }

        // Desenha a imagem de fundo
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            drawImage(
                image = engine.fullResBackground,
                dstSize = IntSize(
                    width = size.width.roundToInt(),
                    height = size.height.roundToInt()
                )
            )
        }

        // Cria o card com efeito Liquid Glass
        LiquidGlassSurface(
            engine = engine,

            // Arredondamento dos cantos
            cornerRadius = 24.dp,

            // Altura da refração do vidro
            refractionHeight = 22.dp,

            // Intensidade da refração
            refractionAmount = 22.dp,

            // Transparência/preenchimento das bordas
            edgeFill = 0.20f,

            // Desativa a interação com o vidro
            isInteractive = false,

            // Posicionamento e tamanho do card
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

            Box(
                modifier = Modifier.fillMaxSize()
            ) {

                // Nossa bolinha Liquid Glass no canto
                LiquidGlassSurface(
                    engine = engine,
                    // Metade do tamanho para formar um círculo
                    cornerRadius = 500.dp,
                    // Configurações do efeito de vidro
                    refractionHeight = 12.dp,
                    refractionAmount = 12.dp,
                    edgeFill = 0.25f,
                    glassOpacity = 1.7f,
                    isInteractive = true,

                    // Tamanho e posição da bolinha
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .offset(
                            x = (37).dp,
                            y = 33.dp
                        )
                        .size(50.dp)
                ) {

                    // Coloca a imagem no centro da bolinha
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {

                        Image(
                            painter = painterResource(
                                id = R.drawable.capybara_chat
                            ),
                            contentDescription = "Ícone da bolinha",
                            modifier = Modifier.size(50.dp)
                        )
                    }
                }
            }
            LiquidGlassSurface(
                engine = engine,
                // Metade do tamanho para formar um círculo
                cornerRadius = 20.dp,
                // Configurações do efeito de vidro
                refractionHeight = 1.dp,
                refractionAmount = 1.dp,
                edgeFill = 0.10f,
                glassOpacity = 1f,
                centerFill = 0f,
                isInteractive = true,

                // Tamanho e posição da bolinha
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(
                        x = 120.dp,
                        y = 33.dp
                    )
                    .size(200.dp,110.dp)
            ){

            }
        }
    }
}