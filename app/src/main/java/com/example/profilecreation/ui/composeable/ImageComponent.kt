package com.example.profilecreation.ui.composeable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.profilecreation.R

@Composable
fun ImageComponent(
    uriState: State<String>,
    onAvatarClick: () -> Unit = {}
) {
    if (uriState.value.isNotEmpty()) {
        val ripple = rememberRipple()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.sign_up_avatar_height))
                .padding(
                    start = dimensionResource(R.dimen.margin_medium),
                    end = dimensionResource(R.dimen.margin_medium),
                    top = dimensionResource(R.dimen.margin_extra_big)
                )
                .wrapContentWidth(align = Alignment.CenterHorizontally)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(uriState.value)
                    .build(),
                contentDescription = "icon",
                contentScale = ContentScale.Inside,
                modifier = Modifier
                    .width(dimensionResource(id = R.dimen.sign_up_avatar_width))
                    .height(dimensionResource(id = R.dimen.sign_up_avatar_height))
                    .clickable(
                        interactionSource = remember {
                            MutableInteractionSource()
                        },
                        indication = ripple,
                        onClick = { onAvatarClick.invoke() })
            )
        }
    }
}
