package com.example.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathBuilder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

object FeatherIcons {
    private fun buildIcon(name: String, block: PathBuilder.() -> Unit): ImageVector {
        return ImageVector.Builder(
            name = name,
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 2.2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                pathBuilder = block
            )
        }.build()
    }

    private fun buildMultiIcon(name: String, paths: List<PathBuilder.() -> Unit>): ImageVector {
        return ImageVector.Builder(
            name = name,
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            paths.forEach { pathBlock ->
                path(
                    stroke = SolidColor(Color.Black),
                    strokeLineWidth = 2.2f,
                    strokeLineCap = StrokeCap.Round,
                    strokeLineJoin = StrokeJoin.Round,
                    pathBuilder = pathBlock
                )
            }
        }.build()
    }

    val ChevronLeft = buildIcon("ChevronLeft") {
        moveTo(15f, 18f)
        lineTo(9f, 12f)
        lineTo(15f, 6f)
    }

    val Home = buildMultiIcon("Home", listOf(
        {
            moveTo(3f, 9f)
            lineTo(12f, 2f)
            lineTo(21f, 9f)
            verticalLineTo(20f)
            curveTo(21f, 21.1f, 20.1f, 22f, 19f, 22f)
            horizontalLineTo(5f)
            curveTo(3.9f, 22f, 3f, 21.1f, 3f, 20f)
            close()
        },
        {
            moveTo(9f, 22f)
            verticalLineTo(12f)
            horizontalLineTo(15f)
            verticalLineTo(22f)
        }
    ))

    val Calendar = buildMultiIcon("Calendar", listOf(
        {
            moveTo(5f, 4f)
            horizontalLineTo(19f)
            curveTo(20.1f, 4f, 21f, 4.9f, 21f, 6f)
            verticalLineTo(20f)
            curveTo(21f, 21.1f, 20.1f, 22f, 19f, 22f)
            horizontalLineTo(5f)
            curveTo(3.9f, 22f, 3f, 21.1f, 3f, 20f)
            verticalLineTo(6f)
            curveTo(3f, 4.9f, 3.9f, 4f, 5f, 4f)
            close()
        },
        {
            moveTo(16f, 2f)
            lineTo(16f, 6f)
        },
        {
            moveTo(8f, 2f)
            lineTo(8f, 6f)
        },
        {
            moveTo(3f, 10f)
            horizontalLineTo(21f)
        }
    ))

    val Plus = buildMultiIcon("Plus", listOf(
        {
            moveTo(12f, 5f)
            lineTo(12f, 19f)
        },
        {
            moveTo(5f, 12f)
            lineTo(19f, 12f)
        }
    ))

    val MessageSquare = buildIcon("MessageSquare") {
        moveTo(21f, 15f)
        curveTo(21f, 16.1f, 20.1f, 17f, 19f, 17f)
        horizontalLineTo(7f)
        lineTo(3f, 21f)
        verticalLineTo(5f)
        curveTo(3f, 3.9f, 3.9f, 3f, 5f, 3f)
        horizontalLineTo(19f)
        curveTo(20.1f, 3f, 21f, 3.9f, 21f, 5f)
        verticalLineTo(15f)
        close()
    }

    val User = buildMultiIcon("User", listOf(
        {
            moveTo(20f, 21f)
            verticalLineTo(19f)
            curveTo(20f, 16.8f, 18.2f, 15f, 16f, 15f)
            horizontalLineTo(8f)
            curveTo(5.8f, 15f, 4f, 16.8f, 4f, 19f)
            verticalLineTo(21f)
        },
        {
            moveTo(12f, 11f)
            curveTo(14.2f, 11f, 16f, 9.2f, 16f, 7f)
            curveTo(16f, 4.8f, 14.2f, 3f, 12f, 3f)
            curveTo(9.8f, 3f, 8f, 4.8f, 8f, 7f)
            curveTo(8f, 9.2f, 9.8f, 11f, 12f, 11f)
            close()
        }
    ))

    val Bell = buildMultiIcon("Bell", listOf(
        {
            moveTo(18f, 8f)
            curveTo(18f, 4.7f, 15.3f, 2f, 12f, 2f)
            curveTo(8.7f, 2f, 6f, 4.7f, 6f, 8f)
            curveTo(6f, 15f, 3f, 17f, 3f, 17f)
            horizontalLineTo(21f)
            curveTo(21f, 17f, 18f, 15f, 18f, 8f)
            close()
        },
        {
            moveTo(13.73f, 21f)
            curveTo(13.52f, 21.36f, 13.14f, 21.6f, 12.72f, 21.6f)
            curveTo(12.3f, 21.6f, 11.92f, 21.36f, 11.71f, 21f)
        }
    ))

    val Search = buildMultiIcon("Search", listOf(
        {
            moveTo(11f, 19f)
            curveTo(15.4f, 19f, 19f, 15.4f, 19f, 11f)
            curveTo(19f, 6.6f, 15.4f, 3f, 11f, 3f)
            curveTo(6.6f, 3f, 3f, 6.6f, 3f, 11f)
            curveTo(3f, 15.4f, 6.6f, 19f, 11f, 19f)
            close()
        },
        {
            moveTo(21f, 21f)
            lineTo(16.65f, 16.65f)
        }
    ))

    val Compass = buildMultiIcon("Compass", listOf(
        {
            moveTo(12f, 22f)
            curveTo(17.5f, 22f, 22f, 17.5f, 22f, 12f)
            curveTo(22f, 6.5f, 17.5f, 2f, 12f, 2f)
            curveTo(6.5f, 2f, 2f, 6.5f, 2f, 12f)
            curveTo(2f, 17.5f, 6.5f, 22f, 12f, 22f)
            close()
        },
        {
            moveTo(16.24f, 7.76f)
            lineTo(14.12f, 14.12f)
            lineTo(7.76f, 16.24f)
            lineTo(9.88f, 9.88f)
            close()
        }
    ))

    val MapPin = buildMultiIcon("MapPin", listOf(
        {
            moveTo(21f, 10f)
            curveTo(21f, 17f, 12f, 23f, 12f, 23f)
            curveTo(12f, 23f, 3f, 17f, 3f, 10f)
            curveTo(3f, 5.03f, 7.03f, 1f, 12f, 1f)
            curveTo(16.97f, 1f, 21f, 5.03f, 21f, 10f)
            close()
        },
        {
            moveTo(12f, 13f)
            curveTo(13.66f, 13f, 15f, 11.66f, 15f, 10f)
            curveTo(15f, 8.34f, 13.66f, 7f, 12f, 7f)
            curveTo(10.34f, 7f, 9f, 8.34f, 9f, 10f)
            curveTo(9f, 11.66f, 10.34f, 13f, 12f, 13f)
            close()
        }
    ))

    val Users = buildMultiIcon("Users", listOf(
        {
            moveTo(17f, 21f)
            verticalLineTo(19f)
            curveTo(17f, 16.8f, 15.2f, 15f, 13f, 15f)
            horizontalLineTo(5f)
            curveTo(2.8f, 15f, 1f, 16.8f, 1f, 19f)
            verticalLineTo(21f)
        },
        {
            moveTo(9f, 11f)
            curveTo(11.2f, 11f, 13f, 9.2f, 13f, 7f)
            curveTo(13f, 4.8f, 11.2f, 3f, 9f, 3f)
            curveTo(6.8f, 3f, 5f, 4.8f, 5f, 7f)
            curveTo(5f, 9.2f, 6.8f, 11f, 9f, 11f)
            close()
        },
        {
            moveTo(23f, 21f)
            verticalLineTo(19f)
            curveTo(23f, 17.5f, 22.1f, 16.2f, 20.8f, 15.5f)
        },
        {
            moveTo(16f, 3.1f)
            curveTo(17.2f, 4.1f, 18f, 5.5f, 18f, 7f)
            curveTo(18f, 8.5f, 17.2f, 9.9f, 16f, 10.9f)
        }
    ))

    val AlertTriangle = buildMultiIcon("AlertTriangle", listOf(
        {
            moveTo(10.29f, 3.86f)
            lineTo(1.82f, 18f)
            curveTo(1.3f, 18.9f, 1.95f, 21f, 3.53f, 21f)
            horizontalLineTo(20.47f)
            curveTo(22.05f, 21f, 22.7f, 18.9f, 22.18f, 18f)
            lineTo(13.71f, 3.86f)
            curveTo(12.94f, 2.5f, 11.06f, 2.5f, 10.29f, 3.86f)
            close()
        },
        {
            moveTo(12f, 9f)
            lineTo(12f, 13f)
        },
        {
            moveTo(12f, 17f)
            lineTo(12.01f, 17f)
        }
    ))

    val CheckCircle = buildMultiIcon("CheckCircle", listOf(
        {
            moveTo(22f, 11.08f)
            verticalLineTo(12f)
            curveTo(22f, 17.52f, 17.52f, 22f, 12f, 22f)
            curveTo(6.48f, 22f, 2f, 17.52f, 2f, 12f)
            curveTo(2f, 6.48f, 6.48f, 2f, 12f, 2f)
            curveTo(14.8f, 2f, 17.3f, 3.15f, 19.1f, 5f)
        },
        {
            moveTo(22f, 4f)
            lineTo(12f, 14.01f)
            lineTo(9f, 11.01f)
        }
    ))

    val Unlock = buildMultiIcon("Unlock", listOf(
        {
            moveTo(5f, 11f)
            horizontalLineTo(19f)
            curveTo(20.1f, 11f, 21f, 11.9f, 21f, 13f)
            verticalLineTo(20f)
            curveTo(21f, 21.1f, 20.1f, 22f, 19f, 22f)
            horizontalLineTo(5f)
            curveTo(3.9f, 22f, 3f, 21.1f, 3f, 20f)
            verticalLineTo(13f)
            curveTo(3f, 11.9f, 3.9f, 11f, 5f, 11f)
            close()
        },
        {
            moveTo(7f, 11f)
            verticalLineTo(7f)
            curveTo(7f, 4.2f, 9.2f, 2f, 12f, 2f)
            curveTo(14.4f, 2f, 16.4f, 3.7f, 16.9f, 6f)
        }
    ))

    val Activity = buildIcon("Activity") {
        moveTo(22f, 12f)
        lineTo(18f, 12f)
        lineTo(15f, 21f)
        lineTo(9f, 3f)
        lineTo(6f, 12f)
        lineTo(2f, 12f)
    }

    val Settings = buildMultiIcon("Settings", listOf(
        {
            moveTo(12f, 15f)
            curveTo(13.66f, 15f, 15f, 13.66f, 15f, 12f)
            curveTo(15f, 10.34f, 13.66f, 9f, 12f, 9f)
            curveTo(10.34f, 9f, 9f, 10.34f, 9f, 12f)
            curveTo(9f, 13.66f, 10.34f, 15f, 12f, 15f)
            close()
        },
        {
            moveTo(19.4f, 15f)
            curveTo(19.6f, 15.6f, 19.3f, 16.3f, 18.8f, 16.8f)
            lineTo(18.5f, 17.1f)
            curveTo(18f, 17.6f, 17.3f, 17.6f, 16.8f, 17.1f)
            curveTo(16.3f, 16.6f, 15.6f, 16.9f, 15f, 17.4f)
            verticalLineTo(18.5f)
            curveTo(15f, 19.1f, 14.5f, 19.6f, 13.9f, 19.6f)
            horizontalLineTo(10.1f)
            curveTo(9.5f, 19.6f, 9f, 19.1f, 9f, 18.5f)
            verticalLineTo(17.4f)
            curveTo(9f, 16.9f, 8.3f, 16.6f, 7.8f, 17.1f)
            curveTo(7.3f, 17.6f, 6.6f, 17.6f, 6.1f, 17.1f)
            lineTo(5.8f, 16.8f)
            curveTo(5.3f, 16.3f, 5f, 15.6f, 5.2f, 15f)
            curveTo(5.4f, 14.5f, 5.1f, 13.9f, 4.6f, 13.9f)
            horizontalLineTo(3.5f)
            curveTo(2.9f, 13.9f, 2.4f, 13.4f, 2.4f, 12.8f)
            verticalLineTo(9.2f)
            curveTo(2.4f, 8.6f, 2.9f, 8.1f, 3.5f, 8.1f)
            horizontalLineTo(4.6f)
            curveTo(5.1f, 8.1f, 5.4f, 7.5f, 5.2f, 7f)
            curveTo(5f, 6.4f, 5.3f, 5.7f, 5.8f, 5.2f)
            lineTo(6.1f, 4.9f)
            curveTo(6.6f, 4.4f, 7.3f, 4.4f, 7.8f, 4.9f)
            curveTo(8.3f, 5.4f, 9f, 5.1f, 9f, 4.6f)
            verticalLineTo(3.5f)
            curveTo(9f, 2.9f, 9.5f, 2.4f, 10.1f, 2.4f)
            horizontalLineTo(13.9f)
            curveTo(14.5f, 2.4f, 15f, 2.9f, 15f, 3.5f)
            verticalLineTo(4.6f)
            curveTo(15f, 5.1f, 15.7f, 5.4f, 16.2f, 5.2f)
            curveTo(16.8f, 5f, 17.5f, 5.3f, 18f, 5.8f)
            lineTo(18.3f, 6.1f)
            curveTo(18.8f, 6.6f, 18.8f, 7.3f, 18.3f, 7.8f)
            curveTo(17.8f, 8.3f, 17.5f, 9f, 18f, 9.6f)
            verticalLineTo(9.6f)
            horizontalLineTo(19.1f)
            curveTo(19.7f, 9.6f, 20.2f, 10.1f, 20.2f, 10.7f)
            verticalLineTo(14.3f)
            curveTo(20.2f, 14.9f, 19.7f, 15.4f, 19.1f, 15.4f)
        }
    ))

    val LogOut = buildMultiIcon("LogOut", listOf(
        {
            moveTo(9f, 21f)
            horizontalLineTo(5f)
            curveTo(3.9f, 21f, 3f, 20.1f, 3f, 19f)
            verticalLineTo(5f)
            curveTo(3f, 3.9f, 3.9f, 3f, 5f, 3f)
            horizontalLineTo(9f)
        },
        {
            moveTo(16f, 17f)
            lineTo(21f, 12f)
            lineTo(16f, 7f)
        },
        {
            moveTo(21f, 12f)
            lineTo(9f, 12f)
        }
    ))

    val Moon = buildIcon("Moon") {
        moveTo(21f, 12.79f)
        curveTo(20.3f, 13f, 19.5f, 13.1f, 18.7f, 13.1f)
        curveTo(14.4f, 13.1f, 10.9f, 9.6f, 10.9f, 5.3f)
        curveTo(10.9f, 4.1f, 11.2f, 3f, 11.7f, 2f)
        curveTo(6.2f, 2.7f, 2f, 7.4f, 2f, 13f)
        curveTo(2f, 18.5f, 6.5f, 23f, 12f, 23f)
        curveTo(17.1f, 23f, 21.3f, 19.2f, 21.9f, 14.3f)
        close()
    }

    val Map = buildMultiIcon("Map", listOf(
        {
            moveTo(3f, 6f)
            lineTo(9f, 3f)
            lineTo(15f, 6f)
            lineTo(21f, 3f)
            verticalLineTo(18f)
            lineTo(15f, 21f)
            lineTo(9f, 18f)
            lineTo(3f, 21f)
            close()
        },
        {
            moveTo(9f, 3f)
            verticalLineTo(18f)
        },
        {
            moveTo(15f, 6f)
            verticalLineTo(21f)
        }
    ))

    val Link = buildMultiIcon("Link", listOf(
        {
            moveTo(10f, 13f)
            curveTo(10.5f, 14.1f, 11.8f, 14.7f, 12.9f, 14.2f)
            lineTo(15.9f, 11.2f)
            curveTo(16.9f, 10.2f, 16.9f, 8.6f, 15.9f, 7.6f)
            lineTo(14.9f, 6.6f)
            curveTo(13.9f, 5.6f, 12.3f, 5.6f, 11.3f, 6.6f)
            lineTo(10.1f, 7.8f)
        },
        {
            moveTo(14f, 11f)
            curveTo(13.5f, 9.9f, 12.2f, 9.3f, 11.1f, 9.8f)
            lineTo(8.1f, 12.8f)
            curveTo(7.1f, 13.8f, 7.1f, 15.4f, 8.1f, 16.4f)
            lineTo(9.1f, 17.4f)
            curveTo(10.1f, 18.4f, 11.7f, 18.4f, 12.7f, 17.4f)
            lineTo(13.9f, 16.2f)
        }
    ))

    val Send = buildMultiIcon("Send", listOf(
        {
            moveTo(22f, 2f)
            lineTo(11f, 13f)
        },
        {
            moveTo(22f, 2f)
            lineTo(15f, 22f)
            lineTo(11f, 13f)
            lineTo(2f, 9f)
            close()
        }
    ))

    val Shield = buildIcon("Shield") {
        moveTo(12f, 22f)
        curveTo(12f, 22f, 20f, 18f, 20f, 12f)
        verticalLineTo(5f)
        lineTo(12f, 2f)
        lineTo(4f, 5f)
        verticalLineTo(12f)
        curveTo(4f, 18f, 12f, 22f, 12f, 22f)
        close()
    }

    val Mail = buildMultiIcon("Mail", listOf(
        {
            moveTo(4f, 4f)
            horizontalLineTo(20f)
            curveTo(21.1f, 4f, 22f, 4.9f, 22f, 6f)
            verticalLineTo(18f)
            curveTo(22f, 19.1f, 21.1f, 20f, 20f, 20f)
            horizontalLineTo(4f)
            curveTo(2.9f, 20f, 2f, 19.1f, 2f, 18f)
            verticalLineTo(6f)
            curveTo(2f, 4.9f, 2.9f, 4f, 4f, 4f)
            close()
        },
        {
            moveTo(22f, 6f)
            lineTo(12f, 13f)
            lineTo(2f, 6f)
        }
    ))

    val MoreVertical = buildMultiIcon("MoreVertical", listOf(
        {
            moveTo(12f, 5f)
            curveTo(12.55f, 5f, 13f, 5.45f, 13f, 6f)
            curveTo(13f, 6.55f, 12.55f, 7f, 12f, 7f)
            curveTo(11.45f, 7f, 11f, 6.55f, 11f, 6f)
            curveTo(11f, 5.45f, 11.45f, 5f, 12f, 5f)
            close()
        },
        {
            moveTo(12f, 11f)
            curveTo(12.55f, 11f, 13f, 11.45f, 13f, 12f)
            curveTo(13f, 12.55f, 12.55f, 13f, 12f, 13f)
            curveTo(11.45f, 13f, 11f, 12.55f, 11f, 12f)
            curveTo(11f, 11.45f, 11.45f, 11f, 12f, 11f)
            close()
        },
        {
            moveTo(12f, 17f)
            curveTo(12.55f, 17f, 13f, 17.45f, 13f, 18f)
            curveTo(13f, 18.55f, 12.55f, 19f, 12f, 19f)
            curveTo(11.45f, 19f, 11f, 18.55f, 11f, 18f)
            curveTo(11f, 17.45f, 11.45f, 17f, 12f, 17f)
            close()
        }
    ))

    val Brush = buildIcon("Brush") {
        moveTo(12f, 22f)
        curveTo(12f, 22f, 15f, 21f, 17f, 19f)
        curveTo(19f, 17f, 20f, 15f, 20f, 12f)
        verticalLineTo(10f)
        lineTo(14f, 4f)
        lineTo(4f, 14f)
        verticalLineTo(20f)
        close()
    }
}
