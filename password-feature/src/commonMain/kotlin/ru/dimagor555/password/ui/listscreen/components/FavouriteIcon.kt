package ru.dimagor555.password.ui.listscreen.components

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ru.dimagor555.res.core.MR
import ru.dimagor555.ui.core.util.stringResource

@Composable
internal fun FavouriteIcon(modifier: Modifier = Modifier, isFavourite: Boolean) {
    val iconTint = if (isFavourite) favouriteColor else notFavouriteColor
    Icon(
        modifier = modifier,
        imageVector = Icons.Default.Star,
        contentDescription = stringResource(MR.strings.toggle_favourite),
        tint = iconTint
    )
}

private val notFavouriteColor = Color(0xffc9c9c9)
private val favouriteColor = Color(0xffffca1c)