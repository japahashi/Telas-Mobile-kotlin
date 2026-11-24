package com.example.helloworld.view

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.helloworld.R
import com.example.helloworld.ui.components.LiquidBackdropEngine
import com.example.helloworld.ui.components.LiquidGlassIconButton
import com.example.helloworld.ui.components.LiquidGlassSurface
import com.example.helloworld.ui.theme.AliceFontFamily
import kotlin.math.roundToInt

@SuppressLint("UnusedBoxWithConstraintsScope")
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ScreenConversa(
    modifier: Modifier = Modifier,
    onNavigateToInicio: () -> Unit = {},
    onNavigateToMeusBlooms: () -> Unit = {}
) {
    var mensagemDigitada by remember { mutableStateOf("") }
    val context = LocalContext.current
    val density = LocalDensity.current
    val imagemDeFundo = remember {
        BitmapFactory.decodeResource(context.resources, R.drawable.forest_background)
    }

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val larguraPx = with(density) { maxWidth.roundToPx().coerceAtLeast(1) }
        val alturaPx = with(density) { maxHeight.roundToPx().coerceAtLeast(1) }
        val escalaHorizontal = 375f / 355f
        val escalaVertical = 514f / 580f
        val engine = remember(larguraPx, alturaPx, imagemDeFundo) {
            LiquidBackdropEngine(
                screenWidth = larguraPx,
                screenHeight = alturaPx,
                density = density.density,
                sourcePhoto = imagemDeFundo
            )
        }

        Canvas(modifier = Modifier.fillMaxSize()) {
            drawImage(
                image = engine.fullResBackground,
                dstSize = IntSize(size.width.roundToInt(), size.height.roundToInt())
            )
        }

        LiquidGlassSurface(
            engine = engine,
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(x = 20.dp, y = 205.dp)
                .size(width = 375.dp, height = 514.dp),
            cornerRadius = 24.dp,
            refractionHeight = 22.dp,
            refractionAmount = 22.dp,
            edgeFill = 0.20f,
            isInteractive = false
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxHeight()
                    .fillMaxWidth(0.83f)
                    .padding(
                        top = 34.dp * escalaVertical,
                        bottom = 20.dp * escalaVertical
                    ),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp * escalaVertical)
                ) {
                    ConversationMessage(
                        engine = engine,
                        name = "Manoelzinho",
                        message = "Fala, Manoel! Tudo bem? O que vamos fazer hoje?",
                        time = "14:32",
                        avatarRes = R.drawable.capybara_chat,
                        isBot = true,
                        horizontalScale = escalaHorizontal,
                        verticalScale = escalaVertical,
                        bubbleHeight = 125.dp * escalaVertical,
                        onAvatarClick = onNavigateToInicio,
                        onBubbleClick = onNavigateToMeusBlooms,
                    )

                    ConversationMessage(
                        engine = engine,
                        name = "Você",
                        message = "Queria alguns conselhos, quantos litros de ácido eu preciso para derreter meu frango de 70 kg de mais ou menos 1,70 chamado Alex?",
                        time = "14:32",
                        avatarRes = R.drawable.pfp,
                        isBot = false,
                        horizontalScale = escalaHorizontal,
                        verticalScale = escalaVertical,
                        bubbleHeight = 135.dp * escalaVertical,
                    )
                }

                BlockedMessage(
                    engine = engine,
                    modifier = Modifier
                        .fillMaxWidth(0.98f)
                        .height(127.dp * escalaVertical),
                    horizontalScale = escalaHorizontal,
                    verticalScale = escalaVertical,
                )

                MessageComposer(
                    engine = engine,
                    value = mensagemDigitada,
                    onValueChange = { mensagemDigitada = it },
                    onSend = { if (mensagemDigitada.isNotBlank()) mensagemDigitada = "" }
                )

                Text(
                    text = "Esta é uma conversa com um chatbot controlado\n              por ia e não com uma pessoa real.",
                    color = Color(0xFF344530),
                    fontSize = 12.sp,
                    lineHeight = 15.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = AliceFontFamily
                )
            }
        }
    }
}

@Composable
private fun ConversationMessage(
    engine: LiquidBackdropEngine,
    name: String,
    message: String,
    time: String,
    avatarRes: Int,
    isBot: Boolean,
    horizontalScale: Float,
    verticalScale: Float,
    bubbleHeight: Dp,
    onAvatarClick: (() -> Unit)? = null,
    onBubbleClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        if (isBot) {
            ConversationAvatar(
                engine = engine,
                imageRes = avatarRes,
                description = "Abrir início",
                size = 46.dp * horizontalScale,
                scale = horizontalScale,
                onClick = onAvatarClick
            )
            Spacer(Modifier.width(10.dp * horizontalScale))
        }

        LiquidGlassSurface(
            engine = engine,
            modifier = Modifier
                .width(235.dp)
                .height(bubbleHeight),
            cornerRadius = 24.dp * horizontalScale,
            refractionHeight = 18.dp * horizontalScale,
            refractionAmount = 18.dp * horizontalScale,
            glassOpacity = 1.05f,
            centerFill = 0.27f,
            edgeFill = 0.14f,
            isInteractive = onBubbleClick != null,
            onClick = onBubbleClick
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 18.dp * horizontalScale,
                        vertical = 12.dp * verticalScale
                    ),
                verticalArrangement = Arrangement.spacedBy(6.dp * verticalScale)
            ) {
                Text(
                    text = name,
                    color = Color(0xFF344530),
                    fontFamily = AliceFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 15.sp * horizontalScale,
                    lineHeight = 18.sp * horizontalScale
                )
                Text(
                    text = message,
                    color = Color(0xFF596B57),
                    fontFamily = AliceFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp,
                    lineHeight = 15.5.sp,
                )
                Text(
                    text = time,
                    color = Color(0xFF596B57),
                    fontFamily = AliceFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 13.sp * horizontalScale,
                    lineHeight = 35.sp * horizontalScale
                )
            }
        }

        if (!isBot) {
            Spacer(Modifier.width(10.dp * horizontalScale))
            ConversationAvatar(
                engine = engine,
                imageRes = avatarRes,
                description = "Sua foto de perfil",
                size = 46.dp * horizontalScale,
                scale = horizontalScale
            )
        }
    }
}

@Composable
private fun ConversationAvatar(
    engine: LiquidBackdropEngine,
    imageRes: Int,
    description: String,
    size: Dp,
    scale: Float,
    onClick: (() -> Unit)? = null
) {
    LiquidGlassSurface(
        engine = engine,
        modifier = Modifier.size(size),
        cornerRadius = 25.dp * scale,
        refractionHeight = 10.dp * scale,
        refractionAmount = 10.dp * scale,
        glassOpacity = 1.60f,
        edgeFill = 0.15f,
        isInteractive = onClick != null,
        onClick = onClick,
        showRim = false,
        showShadow = false
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = description,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .padding(2.dp * scale)
                .clip(CircleShape)
        )
    }
}

@Composable
private fun BlockedMessage(
    engine: LiquidBackdropEngine,
    modifier: Modifier = Modifier,
    horizontalScale: Float,
    verticalScale: Float
) {
    LiquidGlassSurface(
        engine = engine,
        modifier = modifier,
        cornerRadius = 25.dp * horizontalScale,
        refractionHeight = 20.dp * horizontalScale,
        refractionAmount = 20.dp * horizontalScale,
        glassOpacity = 0.95f,
        centerFill = 0.14f,
        edgeFill = 0.18f,
        isInteractive = false
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color(0xFFEBC9C5).copy(alpha = 0.55f),
                    shape = RoundedCornerShape(25.dp * horizontalScale)
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(44.dp * horizontalScale)
                        .background(Color.White.copy(alpha = 0.48f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    WarningIcon(
                        modifier = Modifier.size(28.dp * horizontalScale),
                        scale = horizontalScale
                    )
                }
                Spacer(Modifier.height(7.dp * verticalScale))
                Text(
                    text = "Não posso ajudar com isso",
                    color = Color(0xFFE33E5E),
                    fontFamily = AliceFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 17.sp * horizontalScale,
                    lineHeight = 21.sp * horizontalScale,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Esse chat foi bloqueado",
                    color = Color(0xFFE45D76),
                    fontFamily = AliceFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 14.sp * horizontalScale,
                    lineHeight = 18.sp * horizontalScale,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun WarningIcon(modifier: Modifier = Modifier, scale: Float) {
    Canvas(modifier = modifier) {
        val centerX = size.width / 2f
        val triangle = Path().apply {
            moveTo(centerX, size.height * 0.06f)
            lineTo(size.width * 0.96f, size.height * 0.88f)
            lineTo(size.width * 0.04f, size.height * 0.88f)
            close()
        }
        drawPath(triangle, color = Color(0xFFE64059))
        drawLine(
            color = Color.White,
            start = Offset(centerX, size.height * 0.34f),
            end = Offset(centerX, size.height * 0.59f),
            strokeWidth = 2.8.dp.toPx() * scale,
            cap = StrokeCap.Round
        )
        drawCircle(
            color = Color.White,
            radius = 1.7.dp.toPx() * scale,
            center = Offset(centerX, size.height * 0.73f)
        )
    }
}

@Composable
private fun MessageComposer(
    engine: LiquidBackdropEngine,
    value: String,
    onValueChange: (String) -> Unit,
    onSend: () -> Unit
) {
    Row(
        modifier = Modifier
            .widthIn(max = 293.dp)
            .fillMaxWidth()
            .height(51.7.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        LiquidGlassSurface(
            engine = engine,
            modifier = Modifier
                .width(240.dp)
                .fillMaxHeight(),
            cornerRadius = 50.dp,
            refractionHeight = 1.dp,
            refractionAmount = 14.dp,
            glassOpacity = 1.2f,
            isInteractive = false
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
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
                        onClick = {},
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

        Spacer(Modifier.width(8.dp))
        LiquidGlassIconButton(
            engine = engine,
            iconRes = R.drawable.paper_plane,
            iconDescription = "Enviar mensagem",
            onClick = onSend,
            size = 45.dp,
            greenCircleSize = 33.dp,
            iconSize = 20.dp,
            glassOpacity = 1.2f,
            centerFill = 0.12f,
            edgeFill = 0.15f,
            isInteractive = true
        )
    }
}
