package com.example.profilecreation.ui.composeable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.profilecreation.R


@Composable
fun UserAvatarComponent(uriStr: State<String>, onAvatarClick: () -> Unit) {
    val ripple = rememberRipple()

    val dimensionInPixels =
        LocalContext.current.resources.getDimensionPixelSize(R.dimen.sign_up_avatar_placeholder_radius)

    val shape = remember {
        RoundedCornerShape(dimensionInPixels.dp)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.sign_up_avatar_height))
            .wrapContentWidth(align = Alignment.CenterHorizontally)
    ) {
        if (uriStr.value.isEmpty()) {
            Box(
                modifier = Modifier
                    .width(dimensionResource(id = R.dimen.sign_up_avatar_width))
                    .padding(top = dimensionResource(R.dimen.margin_extra_big))
                    .fillMaxHeight()
                    .background(color = colorResource(id = R.color.black_15_percent), shape = shape)
                    .clip(shape = shape)
                    .clickable(interactionSource = remember {
                        MutableInteractionSource()
                    }, indication = ripple, onClick = { onAvatarClick.invoke() })
            ) {
                Text(
                    text = stringResource(id = R.string.sign_up_placeholder_avatar),
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorResource(R.color.black_35_percent),
                    fontSize = dimensionResource(id = R.dimen.sign_up_avatar_placeholder_text_size).value.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(alignment = Alignment.Center)
                        .padding(all = dimensionResource(R.dimen.margin_medium))
                )
            }
        } else {
            ImageComponent(uriStr, onAvatarClick)
        }
    }
}
