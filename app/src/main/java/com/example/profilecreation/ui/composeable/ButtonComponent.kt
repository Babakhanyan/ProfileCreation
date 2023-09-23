package com.example.profilecreation.ui.composeable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.profilecreation.R

@Composable
fun RippleButton(text: String, onButtonClick: () -> Unit = {}) {
    CompositionLocalProvider(LocalRippleTheme provides RippleCustomTheme) {

        Button(
            onClick = { onButtonClick.invoke() },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.button_background)),
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.button_height))
                .clip(shape = RoundedCornerShape(size = 30.dp))
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                color = colorResource(R.color.white),
                fontSize = dimensionResource(id = R.dimen.button_text_size).value.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally),
            )
        }
    }
}

private object RippleCustomTheme : RippleTheme {
    @Composable
    override fun defaultColor() =
        RippleTheme.defaultRippleColor(
            Color.White,
            lightTheme = true
        )

    @Composable
    override fun rippleAlpha(): RippleAlpha =
        RippleTheme.defaultRippleAlpha(
            Color.Black,
            lightTheme = true
        )
}
