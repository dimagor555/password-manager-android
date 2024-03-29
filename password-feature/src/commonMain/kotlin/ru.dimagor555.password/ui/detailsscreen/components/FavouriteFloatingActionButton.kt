package ru.dimagor555.password.ui.detailsscreen.components

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.runtime.Composable
import ru.dimagor555.res.core.MR
import ru.dimagor555.ui.core.util.stringResource

@Composable
internal fun FavouriteFloatingActionButton(
    isFavourite: Boolean,
    onToggleFavourite: () -> Unit
) {
    FloatingActionButton(onClick = onToggleFavourite) {
        FavouriteIcon(isFavourite = isFavourite)
    }
}

@Composable
private fun FavouriteIcon(isFavourite: Boolean) {
    val iconVector = Icons.Default.run { if (isFavourite) Star else StarOutline }
    Icon(
        imageVector = iconVector,
        contentDescription = stringResource(MR.strings.toggle_favourite),
    )
}