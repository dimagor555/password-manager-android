package ru.dimagor555.password.ui.detailsscreen.components

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.dimagor555.password.ui.R

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
        contentDescription = stringResource(R.string.toggle_favourite),
    )
}