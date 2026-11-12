package com.example.helloworld

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.LinearGradient
import android.graphics.Matrix
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RadialGradient
import android.graphics.RectF
import android.graphics.Shader
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.helloworld.ui.theme.HelloWorldTheme
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tanh
import android.graphics.Canvas as AndroidCanvas
import android.graphics.Paint as AndroidPaint
import android.graphics.Path as AndroidPath
import android.graphics.Rect as AndroidRect
import androidx.compose.ui.text.font.Font

val AliceFontFamily = FontFamily(
    Font(R.font.alice_regular, FontWeight.Normal, FontStyle.Normal)
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            HelloWorldTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    CleanLiquidShowcase(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}

// ============================================================================
// TELA PRINCIPAL
// ============================================================================

@SuppressLint("UnusedBoxWithConstraintsScope")
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun CleanLiquidShowcase(modifier: Modifier = Modifier) {

    var textoDigitado by remember { mutableStateOf("") }

    val density = LocalDensity.current
    val context = LocalContext.current

    val backgroundBitmap = remember {
        BitmapFactory.decodeResource(
            context.resources,
            R.drawable.forest_background
        )
    }

    BoxWithConstraints(modifier = modifier) {

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
                    text = "Olá Manoel",
                    color = Color(0xFF2C3E35),
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
        // 4 BOTÕES COM LIQUID GLASS SEM CONTORNO MARCADO
        // ====================================================================

        // Botão 1: Quero refletir
        LiquidActionButton(
            engine = engine,
            text = "Quero refletir",
            iconRes = R.drawable.cloud,
            iconDescription = "Nuvem",
            textOffsetX = 9.dp,
            onClick = { /* Ação ao clicar */ },
            glassOpacity = 1.5f,
            modifier = Modifier
                .align(Alignment.Center)
                .offset(x = (-80).dp, y = 60.dp)
                .size(145.dp, 60.dp)
        )

        // Botão 2: Meu dia
        LiquidActionButton(
            engine = engine,
            text = "Meu dia",
            iconRes = R.drawable.sun,
            iconDescription = "Sol",
            onClick = { /* Ação ao clicar */ },
            glassOpacity = 1.5f,
            modifier = Modifier
                .align(Alignment.Center)
                .offset(x = 85.dp, y = 60.dp)
                .size(145.dp, 60.dp)
        )

        // Botão 3: Desabafar
        LiquidActionButton(
            engine = engine,
            text = "Desabafar",
            iconRes = R.drawable.leaf,
            iconDescription = "Folha",
            onClick = { /* Ação ao clicar */ },
            glassOpacity = 1.5f,
            modifier = Modifier
                .align(Alignment.Center)
                .offset(x = (-80).dp, y = 135.dp)
                .size(150.dp, 60.dp)
        )

        // Botão 4: Só na conversa
        LiquidActionButton(
            engine = engine,
            text = "Só na\nconversa",
            iconRes = R.drawable.heart,
            iconDescription = "Coração",
            onClick = { /* Ação ao clicar */ },
            glassOpacity = 1.5f,
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
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = 220.dp, x = (-30).dp)
                .size(240.dp, 50.dp)
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
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(50.dp),

                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF435F3F),
                    unfocusedBorderColor = Color.White.copy(alpha = 0f),
                    focusedTextColor = Color(0xFF2C3E35),
                    unfocusedTextColor = Color(0xFF2C3E35),
                    unfocusedContainerColor = Color.White.copy(alpha = 0.25f),
                    focusedContainerColor = Color.White.copy(alpha = 0.25f)
                )
            )
        }

        // ====================================================================
        // BOTÃO DE ENVIAR
        // ====================================================================

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = (-70).dp, y = (-186).dp)
                .size(33.dp, 33.dp)
                .clip(RoundedCornerShape(150.dp))
                .background(Color(0xFF435F3F))
        )

        Image(
            painter = painterResource(id = R.drawable.paper_plane),
            contentDescription = "Avião de papel",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = (-78).dp, y = (-192).dp)
                .size(20.dp, 20.dp)
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
// COMPONENTE AUXILIAR DOS BOTÕES
// ============================================================================

@Composable
fun LiquidActionButton(
    engine: LiquidBackdropEngine,
    text: String,
    iconRes: Int,
    iconDescription: String,
    modifier: Modifier = Modifier,
    textOffsetX: Dp = 0.dp,
    onClick: () -> Unit = {},
    glassOpacity: Float = 1f
) {

    LiquidGlassSurface(
        engine = engine,
        glassOpacity = glassOpacity,
        cornerRadius = 16.dp,
        refractionHeight = 12.dp,
        refractionAmount = 12.dp,
        isInteractive = true,
        onClick = onClick,

        // Remove o contorno óptico e a sombra externa dos botões.
        showRim = false,
        showShadow = false,

        modifier = modifier
    ) {

        Box(modifier = Modifier.fillMaxSize()) {

            // Círculo verde do ícone
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .offset(x = 10.dp)
                    .size(23.dp)
                    .clip(RoundedCornerShape(150.dp))
                    .background(Color(0xFF435F3F))
            )

            // Ícone
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = iconDescription,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(15.dp)
                    .offset(x = 14.dp)
            )

            // Texto do botão
            Text(
                text = text,
                lineHeight = 15.sp,
                color = Color(0xFF2C3E35),
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = AliceFontFamily,
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(x = textOffsetX)
            )

            // Seta direita
            Image(
                painter = painterResource(id = R.drawable.arrow_right),
                contentDescription = "Seta",
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(20.dp)
                    .offset(x = (-3).dp)
            )
        }
    }
}

// ============================================================================
// COMPONENTE PRINCIPAL LIQUID GLASS
// ============================================================================

@Composable
fun LiquidGlassSurface(
    engine: LiquidBackdropEngine,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 20.dp,
    refractionHeight: Dp = 22.dp,
    refractionAmount: Dp = 22.dp,
    isInteractive: Boolean = true,
    onClick: (() -> Unit)? = null,

    // Controla o brilho óptico da borda.
    showRim: Boolean = true,
    // Controla a sombra externa do componente.
    showShadow: Boolean = true,
    glassOpacity: Float = 1f,

    content: @Composable BoxScope.() -> Unit
) {

    val density = LocalDensity.current
    val scope = rememberCoroutineScope()

    var cardPos by remember { mutableStateOf(Offset.Zero) }
    var cardSize by remember { mutableStateOf(IntSize.Zero) }

    val pressProgress = remember { Animatable(0f) }

    var touchOffset by remember { mutableStateOf(Offset.Zero) }

    var lastTouchLocal by remember {
        mutableStateOf(Offset.Unspecified)
    }

    val cornerPx = with(density) {
        cornerRadius.toPx()
    }

    val refHeightPx = with(density) {
        refractionHeight.toPx()
    }

    val refAmountPx = with(density) {
        refractionAmount.toPx()
    }

    val shadowPadPx = with(density) {
        32.dp.roundToPx()
    }

    // GERA OS RECURSOS DO VIDRO
    val glassAssets = remember(
        cardSize,
        cornerPx,
        refHeightPx,
        refAmountPx,
        showRim,
        showShadow,
        glassOpacity
    ) {

        if (cardSize.width > 0 && cardSize.height > 0) {

            GlassCardAssets.build(
                cardW = cardSize.width,
                cardH = cardSize.height,
                cornerRadiusPx = cornerPx,
                refractionHeightPx = refHeightPx,
                refractionAmountPx = refAmountPx,

                // PASSA A CONFIGURAÇÃO DO CONTORNO
                showRim = showRim,
                glassOpacity = glassOpacity,

                shadowPadPx = shadowPadPx,
                density = density.density,
                bufferScale = engine.scaleFactor
            )

        } else null
    }

    val dstRect = remember {
        AndroidRect()
    }

    val bitmapPaint = remember {
        AndroidPaint(
            AndroidPaint.ANTI_ALIAS_FLAG or
                    AndroidPaint.FILTER_BITMAP_FLAG
        )
    }

    Box(
        modifier = modifier

            .onGloballyPositioned { coords ->
                cardPos = coords.positionInParent()
                cardSize = coords.size
            }

            .then(
                if (isInteractive) {

                    Modifier.pointerInput(onClick) {

                        awaitEachGesture {

                            val down = awaitFirstDown(
                                requireUnconsumed = false
                            )

                            val startPos = down.position

                            lastTouchLocal = startPos

                            scope.launch {
                                pressProgress.animateTo(
                                    1f,
                                    spring(
                                        dampingRatio = 0.65f,
                                        stiffness = 380f
                                    )
                                )
                            }

                            var dragged = false

                            while (true) {

                                val event = awaitPointerEvent()

                                val change = event.changes.firstOrNull()
                                    ?: break

                                if (!change.pressed) {

                                    if (!dragged) {
                                        onClick?.invoke()
                                    }

                                    break
                                }

                                val delta = change.position - startPos

                                if (delta.getDistance() > 8f) {
                                    dragged = true
                                }

                                touchOffset = delta
                                lastTouchLocal = change.position
                            }

                            touchOffset = Offset.Zero

                            scope.launch {
                                pressProgress.animateTo(
                                    0f,
                                    spring(
                                        dampingRatio = 0.6f,
                                        stiffness = 300f
                                    )
                                )
                            }
                        }
                    }

                } else Modifier
            )

            // EFEITO ELÁSTICO
            .graphicsLayer {

                if (isInteractive && size.width > 0f && size.height > 0f) {

                    val p = pressProgress.value

                    val baseScale = 1f +
                            (4.dp.toPx() / size.height) * p

                    val maxOffset = size.minDimension

                    val k = 0.04f

                    translationX = maxOffset *
                            tanh(k * touchOffset.x / maxOffset)

                    translationY = maxOffset *
                            tanh(k * touchOffset.y / maxOffset)

                    val maxDragScale = 4.dp.toPx() / size.height

                    val angle = atan2(
                        touchOffset.y,
                        touchOffset.x
                    )

                    val distRatio = (
                            touchOffset.getDistance() /
                                    size.maxDimension
                            ).coerceAtMost(1f)

                    scaleX = baseScale +
                            maxDragScale * abs(cos(angle)) * distRatio

                    scaleY = baseScale +
                            maxDragScale * abs(sin(angle)) * distRatio
                }
            }

    ) {

        // SOMBRA EXTERNA (pode ser desativada individualmente).
        if (showShadow) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val assets = glassAssets ?: return@Canvas

                drawImage(
                    image = assets.outerShadowBitmap,
                    topLeft = Offset(
                        -shadowPadPx.toFloat(),
                        -shadowPadPx.toFloat()
                    )
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(cornerRadius))
        ) {

            Canvas(modifier = Modifier.fillMaxSize()) {

                val assets = glassAssets ?: return@Canvas

                // IMAGEM REFRATADA
                val refractedBmp = assets.updateRefractedBitmap(
                    engine = engine,
                    cardX = cardPos.x,
                    cardY = cardPos.y
                )

                drawIntoCanvas { canvas ->

                    dstRect.set(
                        0,
                        0,
                        size.width.roundToInt(),
                        size.height.roundToInt()
                    )

                    canvas.nativeCanvas.drawBitmap(
                        refractedBmp,
                        null,
                        dstRect,
                        bitmapPaint
                    )
                }

                // BRILHO AO TOCAR
                if (
                    pressProgress.value > 0.01f &&
                    lastTouchLocal != Offset.Unspecified
                ) {

                    drawRect(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color.White.copy(
                                    alpha = 0.18f * pressProgress.value
                                ),
                                Color.Transparent
                            ),
                            center = lastTouchLocal,
                            radius = size.maxDimension * 0.6f
                        )
                    )
                }

                // CAMADA DE VIDRO
                drawImage(
                    image = assets.innerGlassOverlayBitmap,
                    dstSize = IntSize(
                        size.width.roundToInt(),
                        size.height.roundToInt()
                    )
                )
            }

            content()
        }
    }
}

// ============================================================================
// MOTOR DE FUNDO FOTOGRÁFICO
// ============================================================================

class LiquidBackdropEngine(
    val screenWidth: Int,
    val screenHeight: Int,
    val density: Float,
    sourcePhoto: Bitmap? = null
) {

    val scaleFactor = 3

    val bufW = (screenWidth / scaleFactor).coerceAtLeast(64)
    val bufH = (screenHeight / scaleFactor).coerceAtLeast(64)

    val fullResBackground: ImageBitmap

    val sharpVibrantPixels = IntArray(bufW * bufH)
    val blurredVibrantPixels = IntArray(bufW * bufH)

    init {

        val fullBmp = Bitmap.createBitmap(
            screenWidth,
            screenHeight,
            Bitmap.Config.ARGB_8888
        )

        val fullCanvas = AndroidCanvas(fullBmp)

        if (sourcePhoto != null) {

            drawBitmapCenterCrop(
                fullCanvas,
                sourcePhoto,
                screenWidth.toFloat(),
                screenHeight.toFloat()
            )

        } else {

            drawRealisticLandscapeFallback(
                fullCanvas,
                screenWidth.toFloat(),
                screenHeight.toFloat(),
                density
            )
        }

        fullResBackground = fullBmp.asImageBitmap()

        val smallBmp = Bitmap.createScaledBitmap(
            fullBmp,
            bufW,
            bufH,
            true
        )

        smallBmp.getPixels(
            sharpVibrantPixels,
            0,
            bufW,
            0,
            0,
            bufW,
            bufH
        )

        applyVibrancyInPlace(
            sharpVibrantPixels,
            saturation = 1.55f,
            brightness = 1.06f
        )

        System.arraycopy(
            sharpVibrantPixels,
            0,
            blurredVibrantPixels,
            0,
            sharpVibrantPixels.size
        )

        val edgeBlurRadius = (
                (2f * density) / scaleFactor
                ).roundToInt().coerceAtLeast(1)

        gaussianBlur3Pass(
            sharpVibrantPixels,
            bufW,
            bufH,
            edgeBlurRadius
        )

        val bodyBlurRadius = (
                (16f * density) / scaleFactor
                ).roundToInt().coerceAtLeast(3)

        gaussianBlur3Pass(
            blurredVibrantPixels,
            bufW,
            bufH,
            bodyBlurRadius
        )
    }

    private fun drawBitmapCenterCrop(
        canvas: AndroidCanvas,
        src: Bitmap,
        dstW: Float,
        dstH: Float
    ) {

        val srcW = src.width.toFloat()
        val srcH = src.height.toFloat()

        val scale = max(
            dstW / srcW,
            dstH / srcH
        )

        val dx = (dstW - srcW * scale) * 0.5f
        val dy = (dstH - srcH * scale) * 0.5f

        val matrix = Matrix().apply {
            setScale(scale, scale)
            postTranslate(dx, dy)
        }

        val paint = AndroidPaint(
            AndroidPaint.ANTI_ALIAS_FLAG or
                    AndroidPaint.FILTER_BITMAP_FLAG
        )

        canvas.drawBitmap(src, matrix, paint)
    }

    private fun drawRealisticLandscapeFallback(
        canvas: AndroidCanvas,
        w: Float,
        h: Float,
        d: Float
    ) {

        val paint = AndroidPaint(AndroidPaint.ANTI_ALIAS_FLAG)

        paint.shader = LinearGradient(
            0f,
            0f,
            0f,
            h * 0.65f,
            intArrayOf(
                0xFF4A7C9B.toInt(),
                0xFF8AB8C9.toInt(),
                0xFFF3D5B5.toInt(),
                0xFFF7B28B.toInt()
            ),
            floatArrayOf(0f, 0.45f, 0.82f, 1f),
            Shader.TileMode.CLAMP
        )

        canvas.drawRect(0f, 0f, w, h, paint)

        val sunX = w * 0.72f
        val sunY = h * 0.36f
        val sunR = 110f * d

        paint.shader = RadialGradient(
            sunX,
            sunY,
            sunR,
            intArrayOf(
                0xFFFFF6E0.toInt(),
                0x99FFD19A.toInt(),
                0x00FFD19A
            ),
            floatArrayOf(0f, 0.45f, 1f),
            Shader.TileMode.CLAMP
        )

        canvas.drawCircle(
            sunX,
            sunY,
            sunR,
            paint
        )

        val mtn1 = AndroidPath().apply {

            moveTo(0f, h * 0.58f)
            lineTo(w * 0.22f, h * 0.43f)
            lineTo(w * 0.46f, h * 0.51f)
            lineTo(w * 0.76f, h * 0.39f)
            lineTo(w, h * 0.48f)
            lineTo(w, h)
            lineTo(0f, h)
            close()
        }

        paint.shader = LinearGradient(
            0f,
            h * 0.39f,
            0f,
            h * 0.65f,
            intArrayOf(
                0xFF3E6373.toInt(),
                0xFF6B8F99.toInt()
            ),
            null,
            Shader.TileMode.CLAMP
        )

        canvas.drawPath(mtn1, paint)

        val mtn2 = AndroidPath().apply {

            moveTo(0f, h * 0.54f)
            lineTo(w * 0.34f, h * 0.46f)
            lineTo(w * 0.66f, h * 0.55f)
            lineTo(w, h * 0.47f)
            lineTo(w, h)
            lineTo(0f, h)
            close()
        }

        paint.shader = LinearGradient(
            0f,
            h * 0.46f,
            0f,
            h * 0.72f,
            intArrayOf(
                0xFF1F4E46.toInt(),
                0xFF112E2A.toInt()
            ),
            null,
            Shader.TileMode.CLAMP
        )

        canvas.drawPath(mtn2, paint)

        paint.shader = LinearGradient(
            0f,
            h * 0.62f,
            0f,
            h,
            intArrayOf(
                0xFF287672.toInt(),
                0xFF195456.toInt(),
                0xFF0D2E33.toInt()
            ),
            floatArrayOf(0f, 0.5f, 1f),
            Shader.TileMode.CLAMP
        )

        canvas.drawRect(
            0f,
            h * 0.62f,
            w,
            h,
            paint
        )
    }
}

// ============================================================================
// GERADOR ÓPTICO DO LIQUID GLASS
// ============================================================================

class GlassCardAssets(
    val lensW: Int,
    val lensH: Int,
    val bufferScale: Int,
    val offsetX_R: FloatArray,
    val offsetY_R: FloatArray,
    val offsetX_G: FloatArray,
    val offsetY_G: FloatArray,
    val offsetX_B: FloatArray,
    val offsetY_B: FloatArray,
    val rimEdgeFactor: IntArray,
    val causticBoost: IntArray,
    val outerShadowBitmap: ImageBitmap,
    val innerGlassOverlayBitmap: ImageBitmap
) {

    private val outPixels = IntArray(lensW * lensH)

    private val outBitmap = Bitmap.createBitmap(
        lensW,
        lensH,
        Bitmap.Config.ARGB_8888
    )

    fun updateRefractedBitmap(
        engine: LiquidBackdropEngine,
        cardX: Float,
        cardY: Float
    ): Bitmap {

        val bgW = engine.bufW
        val bgH = engine.bufH

        val blurred = engine.blurredVibrantPixels
        val sharp = engine.sharpVibrantPixels

        val baseBx = cardX / bufferScale
        val baseBy = cardY / bufferScale

        val total = lensW * lensH

        for (i in 0 until total) {

            val lx = i % lensW
            val ly = i / lensW

            val bx = baseBx + lx
            val by = baseBy + ly

            val rx = (
                    bx + offsetX_R[i]
                    ).toInt().coerceIn(0, bgW - 1)

            val ry = (
                    by + offsetY_R[i]
                    ).toInt().coerceIn(0, bgH - 1)

            val gx = (
                    bx + offsetX_G[i]
                    ).toInt().coerceIn(0, bgW - 1)

            val gy = (
                    by + offsetY_G[i]
                    ).toInt().coerceIn(0, bgH - 1)

            val blx = (
                    bx + offsetX_B[i]
                    ).toInt().coerceIn(0, bgW - 1)

            val bly = (
                    by + offsetY_B[i]
                    ).toInt().coerceIn(0, bgH - 1)

            val idxR = ry * bgW + rx
            val idxG = gy * bgW + gx
            val idxB = bly * bgW + blx

            val rimMix = rimEdgeFactor[i]
            val invMix = 255 - rimMix

            val r = (
                    (
                            (blurred[idxR] shr 16 and 0xFF) * invMix +
                                    (sharp[idxR] shr 16 and 0xFF) * rimMix
                            ) shr 8
                    )

            val g = (
                    (
                            (blurred[idxG] shr 8 and 0xFF) * invMix +
                                    (sharp[idxG] shr 8 and 0xFF) * rimMix
                            ) shr 8
                    )

            val b = (
                    (
                            (blurred[idxB] and 0xFF) * invMix +
                                    (sharp[idxB] and 0xFF) * rimMix
                            ) shr 8
                    )

            val boost = causticBoost[i]

            val finalR = (r + boost).coerceAtMost(255)
            val finalG = (g + boost).coerceAtMost(255)
            val finalB = (b + boost).coerceAtMost(255)

            outPixels[i] =
                (0xFF shl 24) or
                        (finalR shl 16) or
                        (finalG shl 8) or
                        finalB
        }

        outBitmap.setPixels(
            outPixels,
            0,
            lensW,
            0,
            0,
            lensW,
            lensH
        )

        return outBitmap
    }

    companion object {

        fun build(
            cardW: Int,
            cardH: Int,
            cornerRadiusPx: Float,
            refractionHeightPx: Float,
            refractionAmountPx: Float,
            showRim: Boolean,
            glassOpacity: Float,
            shadowPadPx: Int,
            density: Float,
            bufferScale: Int
        ): GlassCardAssets {

            val lensW = (
                    cardW / bufferScale
                    ).coerceAtLeast(16)

            val lensH = (
                    cardH / bufferScale
                    ).coerceAtLeast(16)

            val total = lensW * lensH

            val offsetX_R = FloatArray(total)
            val offsetY_R = FloatArray(total)

            val offsetX_G = FloatArray(total)
            val offsetY_G = FloatArray(total)

            val offsetX_B = FloatArray(total)
            val offsetY_B = FloatArray(total)

            val rimEdgeFactor = IntArray(total)
            val causticBoost = IntArray(total)

            val halfW = lensW / 2f
            val halfH = lensH / 2f

            val rScaled = (
                    cornerRadiusPx / bufferScale
                    ).coerceAtMost(min(halfW, halfH))

            val refHeightScaled = (
                    refractionHeightPx / bufferScale
                    ).coerceAtLeast(1f)

            val refAmountScaled = refractionAmountPx / bufferScale

            fun sdfRoundedRect(
                px: Float,
                py: Float
            ): Float {

                val qx = abs(px - halfW) - (halfW - rScaled)
                val qy = abs(py - halfH) - (halfH - rScaled)

                val outDist = sqrt(
                    max(qx, 0f) * max(qx, 0f) +
                            max(qy, 0f) * max(qy, 0f)
                )

                val inDist = min(
                    max(qx, qy),
                    0f
                )

                return outDist + inDist - rScaled
            }

            for (y in 0 until lensH) {

                for (x in 0 until lensW) {

                    val i = y * lensW + x

                    val px = x + 0.5f
                    val py = y + 0.5f

                    val sdf = sdfRoundedRect(px, py)

                    val distFromBorder = -sdf

                    if (distFromBorder in 0f..refHeightScaled) {

                        val gx =
                            sdfRoundedRect(px + 1f, py) -
                                    sdfRoundedRect(px - 1f, py)

                        val gy =
                            sdfRoundedRect(px, py + 1f) -
                                    sdfRoundedRect(px, py - 1f)

                        val len = sqrt(
                            gx * gx + gy * gy
                        ).coerceAtLeast(0.0001f)

                        val nx = gx / len
                        val ny = gy / len

                        val u = (
                                1f - (distFromBorder / refHeightScaled)
                                ).coerceIn(0f, 1f)

                        val lensCurve =
                            u * u * (2.05f - 1.05f * u)

                        val disp = -refAmountScaled * lensCurve

                        // REFRAÇÃO CONTINUA FUNCIONANDO
                        offsetX_R[i] = nx * disp * 1.04f
                        offsetY_R[i] = ny * disp * 1.04f

                        offsetX_G[i] = nx * disp * 1.00f
                        offsetY_G[i] = ny * disp * 1.00f

                        offsetX_B[i] = nx * disp * 0.96f
                        offsetY_B[i] = ny * disp * 0.96f

                        // ====================================================
                        // CONTROLE DO CONTORNO
                        // ====================================================

                        if (showRim) {

                            rimEdgeFactor[i] = (
                                    u * u * 115f
                                    ).toInt().coerceIn(0, 115)

                            val ring = sin(
                                u * Math.PI.toFloat()
                            )

                            causticBoost[i] = (
                                    ring * ring * 7f
                                    ).toInt()

                        } else {

                            // REMOVE O BRILHO DA BORDA
                            rimEdgeFactor[i] = 0
                            causticBoost[i] = 0
                        }
                    }
                }
            }

            val outerShadowBmp = buildOuterShadowBitmap(
                cardW,
                cardH,
                cornerRadiusPx,
                shadowPadPx,
                density
            )

            val innerOverlayBmp = buildInnerGlassOverlayBitmap(
                cardW,
                cardH,
                cornerRadiusPx,
                density,
                glassOpacity
            )

            return GlassCardAssets(
                lensW = lensW,
                lensH = lensH,
                bufferScale = bufferScale,

                offsetX_R = offsetX_R,
                offsetY_R = offsetY_R,

                offsetX_G = offsetX_G,
                offsetY_G = offsetY_G,

                offsetX_B = offsetX_B,
                offsetY_B = offsetY_B,

                rimEdgeFactor = rimEdgeFactor,
                causticBoost = causticBoost,

                outerShadowBitmap = outerShadowBmp.asImageBitmap(),
                innerGlassOverlayBitmap = innerOverlayBmp.asImageBitmap()
            )
        }

        // ================================================================
        // SOMBRA EXTERNA
        // ================================================================

        private fun buildOuterShadowBitmap(
            cardW: Int,
            cardH: Int,
            cornerRadiusPx: Float,
            padPx: Int,
            density: Float
        ): Bitmap {

            val fullW = cardW + padPx * 2
            val fullH = cardH + padPx * 2

            val scale = 2

            val sw = (fullW / scale).coerceAtLeast(8)
            val sh = (fullH / scale).coerceAtLeast(8)

            val shadowBmp = Bitmap.createBitmap(
                sw,
                sh,
                Bitmap.Config.ARGB_8888
            )

            val canvas = AndroidCanvas(shadowBmp)

            val paint = AndroidPaint(
                AndroidPaint.ANTI_ALIAS_FLAG
            )

            val spread1 = -6f * density / scale
            val offY1 = 12f * density / scale

            val rect1 = RectF(
                (padPx / scale) - spread1,
                (padPx / scale) - spread1 + offY1,
                ((padPx + cardW) / scale) + spread1,
                ((padPx + cardH) / scale) + spread1 + offY1
            )

            paint.color = 0x220F2012

            val r1 = (
                    cornerRadiusPx / scale + spread1
                    ).coerceAtLeast(0f)

            canvas.drawRoundRect(
                rect1,
                r1,
                r1,
                paint
            )

            val pixels1 = IntArray(sw * sh)

            shadowBmp.getPixels(
                pixels1,
                0,
                sw,
                0,
                0,
                sw,
                sh
            )

            gaussianBlur3Pass(
                pixels1,
                sw,
                sh,
                (
                        (12f * density) / scale
                        ).roundToInt().coerceAtLeast(2)
            )

            shadowBmp.eraseColor(0)

            val spread2 = -2f * density / scale
            val offY2 = 3f * density / scale

            val rect2 = RectF(
                (padPx / scale) - spread2,
                (padPx / scale) - spread2 + offY2,
                ((padPx + cardW) / scale) + spread2,
                ((padPx + cardH) / scale) + spread2 + offY2
            )

            paint.color = 0x10000000

            val r2 = (
                    cornerRadiusPx / scale + spread2
                    ).coerceAtLeast(0f)

            canvas.drawRoundRect(
                rect2,
                r2,
                r2,
                paint
            )

            val pixels2 = IntArray(sw * sh)

            shadowBmp.getPixels(
                pixels2,
                0,
                sw,
                0,
                0,
                sw,
                sh
            )

            gaussianBlur3Pass(
                pixels2,
                sw,
                sh,
                (
                        (4f * density) / scale
                        ).roundToInt().coerceAtLeast(1)
            )

            val finalSmallBmp = Bitmap.createBitmap(
                sw,
                sh,
                Bitmap.Config.ARGB_8888
            )

            finalSmallBmp.setPixels(
                pixels1,
                0,
                sw,
                0,
                0,
                sw,
                sh
            )

            val combineCanvas = AndroidCanvas(finalSmallBmp)

            shadowBmp.setPixels(
                pixels2,
                0,
                sw,
                0,
                0,
                sw,
                sh
            )

            combineCanvas.drawBitmap(
                shadowBmp,
                0f,
                0f,
                null
            )

            val fullShadowBmp = Bitmap.createScaledBitmap(
                finalSmallBmp,
                fullW,
                fullH,
                true
            )

            val fullCanvas = AndroidCanvas(fullShadowBmp)

            val clearPaint = AndroidPaint(
                AndroidPaint.ANTI_ALIAS_FLAG
            ).apply {
                xfermode = PorterDuffXfermode(
                    PorterDuff.Mode.CLEAR
                )
            }

            fullCanvas.drawRoundRect(
                RectF(
                    padPx.toFloat(),
                    padPx.toFloat(),
                    (padPx + cardW).toFloat(),
                    (padPx + cardH).toFloat()
                ),
                cornerRadiusPx,
                cornerRadiusPx,
                clearPaint
            )

            return fullShadowBmp
        }

        // ================================================================
        // CAMADA INTERNA DO VIDRO
        // ================================================================

        private fun buildInnerGlassOverlayBitmap(
            cardW: Int,
            cardH: Int,
            cornerRadiusPx: Float,
            density: Float,
            glassOpacity: Float

        ): Bitmap {

            val resultBmp = Bitmap.createBitmap(
                cardW,
                cardH,
                Bitmap.Config.ARGB_8888
            )

            val canvas = AndroidCanvas(resultBmp)

            val w = cardW.toFloat()
            val h = cardH.toFloat()

            val borderPx = 0f

            val innerR = cornerRadiusPx

            val paddingBoxPath = AndroidPath().apply {

                addRoundRect(
                    RectF(
                        borderPx,
                        borderPx,
                        w - borderPx,
                        h - borderPx
                    ),
                    innerR,
                    innerR,
                    AndroidPath.Direction.CW
                )
            }

            canvas.save()
            canvas.clipPath(paddingBoxPath)

            val paint = AndroidPaint(
                AndroidPaint.ANTI_ALIAS_FLAG
            )
            fun adjustAlpha(color: Int): Int {
                val alpha = (((color ushr 24) * glassOpacity).toInt()).coerceIn(0, 255)
                return (color and 0x00FFFFFF) or (alpha shl 24)
            }

            // FUNDO GRADIENTE DO VIDRO
            paint.shader = LinearGradient(
                0f,
                0f,
                w,
                h,
                intArrayOf(
                    adjustAlpha(0x55FFFFFF),
                    adjustAlpha(0x55FFFFFF),
                    adjustAlpha(0x55FFFFFF)
                ),
                floatArrayOf(0f, 0.5f, 1f),
                Shader.TileMode.CLAMP
            )

            canvas.drawRect(
                0f,
                0f,
                w,
                h,
                paint
            )

            // BRILHO SUPERIOR ESQUERDO
            val cx = w * 0.30f
            val cy = 0f

            val rx = (
                    w * 1.30f
                    ).coerceAtLeast(1f)

            val ry = (
                    h * 0.85f
                    ).coerceAtLeast(1f)

            val radialShader = RadialGradient(
                cx,
                cy,
                rx,
                intArrayOf(
                    adjustAlpha(0x79FFFFFF),
                    adjustAlpha(0x49FFFFFF),
                    adjustAlpha(0x79FFFFFF)
                ),
                floatArrayOf(0f, 0.5f, 1f),
                Shader.TileMode.CLAMP
            )

            val ellipseMatrix = Matrix().apply {
                setScale(
                    1f,
                    ry / rx,
                    cx,
                    cy
                )
            }

            radialShader.setLocalMatrix(ellipseMatrix)

            paint.shader = radialShader

            canvas.drawRect(
                0f,
                0f,
                w,
                h,
                paint
            )

            paint.shader = null

            return resultBmp
        }
    }
}

// ============================================================================
// PROCESSAMENTO DE IMAGEM: VIBRANCY E GAUSSIAN BLUR
// ============================================================================

private fun applyVibrancyInPlace(
    pixels: IntArray,
    saturation: Float,
    brightness: Float
) {

    for (i in pixels.indices) {

        val c = pixels[i]

        val a = c ushr 24

        var r = (c shr 16 and 0xFF).toFloat()
        var g = (c shr 8 and 0xFF).toFloat()
        var b = (c and 0xFF).toFloat()

        val lum =
            0.2126f * r +
                    0.7152f * g +
                    0.0722f * b

        r = (lum + (r - lum) * saturation) * brightness
        g = (lum + (g - lum) * saturation) * brightness
        b = (lum + (b - lum) * saturation) * brightness

        val ir = r.toInt().coerceIn(0, 255)
        val ig = g.toInt().coerceIn(0, 255)
        val ib = b.toInt().coerceIn(0, 255)

        pixels[i] =
            (a shl 24) or
                    (ir shl 16) or
                    (ig shl 8) or
                    ib
    }
}

// ============================================================================
// GAUSSIAN BLUR
// ============================================================================

private fun gaussianBlur3Pass(
    pixels: IntArray,
    w: Int,
    h: Int,
    radius: Int
) {

    if (radius < 1) return

    val temp = IntArray(w * h)

    repeat(3) {

        boxBlurHorizontal(
            pixels,
            temp,
            w,
            h,
            radius
        )

        boxBlurVertical(
            temp,
            pixels,
            w,
            h,
            radius
        )
    }
}

// ============================================================================
// BLUR HORIZONTAL
// ============================================================================

private fun boxBlurHorizontal(
    src: IntArray,
    dst: IntArray,
    w: Int,
    h: Int,
    r: Int
) {

    val div = 2 * r + 1

    for (y in 0 until h) {

        var sumA = 0
        var sumR = 0
        var sumG = 0
        var sumB = 0

        val rowOffset = y * w

        for (i in -r..r) {

            val c = src[
                rowOffset + i.coerceIn(0, w - 1)
            ]

            sumA += (c ushr 24) and 0xFF
            sumR += (c shr 16) and 0xFF
            sumG += (c shr 8) and 0xFF
            sumB += c and 0xFF
        }

        for (x in 0 until w) {

            dst[rowOffset + x] =
                ((sumA / div) shl 24) or
                        ((sumR / div) shl 16) or
                        ((sumG / div) shl 8) or
                        (sumB / div)

            val outIdx = (
                    x - r
                    ).coerceIn(0, w - 1)

            val inIdx = (
                    x + r + 1
                    ).coerceIn(0, w - 1)

            val cOut = src[rowOffset + outIdx]
            val cIn = src[rowOffset + inIdx]

            sumA +=
                ((cIn ushr 24) and 0xFF) -
                        ((cOut ushr 24) and 0xFF)

            sumR +=
                ((cIn shr 16) and 0xFF) -
                        ((cOut shr 16) and 0xFF)

            sumG +=
                ((cIn shr 8) and 0xFF) -
                        ((cOut shr 8) and 0xFF)

            sumB +=
                (cIn and 0xFF) -
                        (cOut and 0xFF)
        }
    }
}

// ============================================================================
// BLUR VERTICAL
// ============================================================================

private fun boxBlurVertical(
    src: IntArray,
    dst: IntArray,
    w: Int,
    h: Int,
    r: Int
) {

    val div = 2 * r + 1

    for (x in 0 until w) {

        var sumA = 0
        var sumR = 0
        var sumG = 0
        var sumB = 0

        for (i in -r..r) {

            val c = src[
                i.coerceIn(0, h - 1) * w + x
            ]

            sumA += (c ushr 24) and 0xFF
            sumR += (c shr 16) and 0xFF
            sumG += (c shr 8) and 0xFF
            sumB += c and 0xFF
        }

        for (y in 0 until h) {

            dst[y * w + x] =
                ((sumA / div) shl 24) or
                        ((sumR / div) shl 16) or
                        ((sumG / div) shl 8) or
                        (sumB / div)

            val outIdx = (
                    y - r
                    ).coerceIn(0, h - 1)

            val inIdx = (
                    y + r + 1
                    ).coerceIn(0, h - 1)

            val cOut = src[outIdx * w + x]
            val cIn = src[inIdx * w + x]

            sumA +=
                ((cIn ushr 24) and 0xFF) -
                        ((cOut ushr 24) and 0xFF)

            sumR +=
                ((cIn shr 16) and 0xFF) -
                        ((cOut shr 16) and 0xFF)

            sumG +=
                ((cIn shr 8) and 0xFF) -
                        ((cOut shr 8) and 0xFF)

            sumB +=
                (cIn and 0xFF) -
                        (cOut and 0xFF)
        }
    }
}