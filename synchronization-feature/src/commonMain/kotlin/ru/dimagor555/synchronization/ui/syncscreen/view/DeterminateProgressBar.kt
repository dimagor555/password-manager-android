package ru.dimagor555.synchronization.ui.syncscreen.view

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DeterminateProgressBar(
    modifier: Modifier = Modifier,
    progressColor: Color = MaterialTheme.colors.primaryVariant,
    progressBackgroundColor: Color = MaterialTheme.colors.primary,
    strokeWidth: Dp = 10.dp,
    strokeBackgroundWidth: Dp = 9.8.dp,
    progress: Float = 90f,
    progressDirection: AnimationDirection = AnimationDirection.RIGHT,
    roundedBorder: Boolean = true,
    durationInMilliSecond: Int = 2000,
    startDelay: Int = 1000,
    radius: Dp = 80.dp,
) {
    val stroke = with(LocalDensity.current) {
        Stroke(width = strokeWidth.toPx(), cap = if(roundedBorder) StrokeCap.Round else StrokeCap.Square)
    }

    val strokeBackground = with(LocalDensity.current) {
        Stroke(width = strokeBackgroundWidth.toPx())
    }

    val currentState = remember {
        MutableTransitionState(AnimatedArcState.START)
            .apply { targetState = AnimatedArcState.END }
    }
    val animatedProgress = updateTransition(currentState)
    var isFinished by remember { mutableStateOf(false) }

    val progress by animatedProgress.animateFloat(
        transitionSpec = {
            tween(
                durationMillis = durationInMilliSecond,
                easing = LinearEasing,
                delayMillis = startDelay
            )
        }
    ) { state ->
        when (state) {
            AnimatedArcState.START -> 0f
            AnimatedArcState.END -> {
                when(progressDirection) {
                    AnimationDirection.RIGHT -> progress
                    AnimationDirection.LEFT -> -progress
                }
            }
        }
    }

    DisposableEffect(Unit) {
        isFinished = animatedProgress.currentState == animatedProgress.targetState
        onDispose {}
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(radius * 2)
    ) {
        Canvas(
            modifier.size(radius * 2)
        ) {

            val higherStrokeWidth =
                if (stroke.width > strokeBackground.width) stroke.width else strokeBackground.width
            val radius = (size.minDimension - higherStrokeWidth) / 2
            val halfSize = size / 2.0f
            val topLeft = Offset(
                halfSize.width - radius,
                halfSize.height - radius
            )
            val size = Size(radius * 2, radius * 2)
            val sweep = progress * 360 / 100
            isFinished = animatedProgress.currentState == animatedProgress.targetState

            drawArc(
                startAngle = 0f,
                sweepAngle = 360f,
                color = progressBackgroundColor,
                useCenter = false,
                topLeft = topLeft,
                size = size,
                style = strokeBackground
            )

            drawArc(
                color = progressColor,
                startAngle = 270f,
                sweepAngle = sweep,
                useCenter = false,
                topLeft = topLeft,
                size = size,
                style = stroke
            )
        }

        Text(
            text = progress.toInt().toString() + "%",
            color = progressColor,
            fontSize = radius.value.sp / 2,
            fontWeight = FontWeight.SemiBold,
            style = typography.subtitle1
        )
    }
}

private enum class AnimatedArcState {
    START,
    END
}

enum class AnimationDirection {
    LEFT,
    RIGHT
}
