package ru.dimagor555.ui.core.icon

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import ru.dimagor555.ui.core.util.Preview

val Icons.Filled.Lock: ImageVector
    get() {
        if (_lock != null) {
            return _lock!!
        }
        _lock = Builder(
            name = "Lock", defaultWidth = 498.0.dp, defaultHeight = 498.0.dp,
            viewportWidth = 498.0f, viewportHeight = 498.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF26C6DB)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(413.0f, 163.0f)
                horizontalLineTo(378.0f)
                verticalLineTo(122.0f)
                curveTo(378.0f, 55.0f, 323.0f, 0.0f, 256.0f, 0.0f)
                horizontalLineTo(240.0f)
                curveTo(173.0f, 0.0f, 118.0f, 55.0f, 118.0f, 122.0f)
                verticalLineTo(163.0f)
                horizontalLineTo(83.0f)
                curveTo(62.0f, 163.0f, 45.0f, 180.0f, 45.0f, 202.0f)
                verticalLineTo(459.0f)
                curveTo(45.0f, 481.0f, 62.0f, 498.0f, 83.0f, 498.0f)
                horizontalLineTo(413.0f)
                curveTo(434.0f, 498.0f, 452.0f, 481.0f, 452.0f, 459.0f)
                verticalLineTo(202.0f)
                curveTo(452.0f, 180.0f, 434.0f, 163.0f, 413.0f, 163.0f)
                close()
                moveTo(279.0f, 323.0f)
                verticalLineTo(381.0f)
                curveTo(279.0f, 394.0f, 268.0f, 405.0f, 254.0f, 405.0f)
                reflectiveCurveTo(230.0f, 394.0f, 230.0f, 381.0f)
                verticalLineTo(323.0f)
                curveTo(219.0f, 316.0f, 212.0f, 303.0f, 212.0f, 289.0f)
                curveTo(212.0f, 265.0f, 231.0f, 246.0f, 254.0f, 246.0f)
                reflectiveCurveTo(297.0f, 265.0f, 297.0f, 289.0f)
                curveTo(297.0f, 303.0f, 289.0f, 316.0f, 279.0f, 323.0f)
                close()
                moveTo(168.0f, 163.0f)
                verticalLineTo(122.0f)
                curveTo(168.0f, 82.0f, 200.0f, 50.0f, 240.0f, 50.0f)
                horizontalLineTo(256.0f)
                curveTo(296.0f, 50.0f, 328.0f, 82.0f, 328.0f, 122.0f)
                verticalLineTo(163.0f)
                horizontalLineTo(168.0f)
                close()
            }
        }
            .build()
        return _lock!!
    }

private var _lock: ImageVector? = null

@Preview
@Composable
private fun LockIconPreview() {
    Icon(imageVector = Icons.Default.Lock, contentDescription = null)
}