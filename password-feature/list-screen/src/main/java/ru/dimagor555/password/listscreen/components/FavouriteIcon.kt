package ru.dimagor555.password.listscreen.components

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import ru.dimagor555.password.listscreen.R

@Composable
internal fun FavouriteIcon(modifier: Modifier = Modifier, isFavourite: Boolean) {
    val iconTint = if (isFavourite) favouriteColor else notFavouriteColor
    Icon(
        modifier = modifier,
        imageVector = Icons.Default.Star,
        contentDescription = stringResource(R.string.toggle_favourite),
        tint = iconTint
    )
}

private val notFavouriteColor = Color(0xffc9c9c9)
private val favouriteColor = Color(0xffffca1c)