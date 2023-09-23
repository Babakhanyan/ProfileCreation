package com.example.profilecreation.ui.composeable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.example.profilecreation.R

@Composable
fun Title(title: String) {
    if (title.isNotEmpty()) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = colorResource(R.color.black_50_percent),
            fontSize = dimensionResource(id = R.dimen.title_text_size).value.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = dimensionResource(R.dimen.margin_medium),
                    end = dimensionResource(R.dimen.margin_medium),
                    top = dimensionResource(R.dimen.margin_medium)
                )
                .wrapContentWidth(Alignment.Start),
        )
    }
}

@Composable
fun Description(description: String) {
    if (description.isNotEmpty()) {
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = colorResource(R.color.black_35_percent),
            fontSize = dimensionResource(id = R.dimen.description_text_size).value.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = dimensionResource(R.dimen.margin_medium),
                    end = dimensionResource(R.dimen.margin_medium),
                    top = dimensionResource(R.dimen.margin_medium)
                )
                .wrapContentWidth(Alignment.Start),
        )
    }
}

@Composable
fun Name(name: String) {
    if (name.isNotEmpty()) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium,
            color = colorResource(R.color.black_50_percent),
            fontSize = dimensionResource(id = R.dimen.info_text_size).value.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(R.dimen.margin_medium))
                .wrapContentWidth(Alignment.CenterHorizontally),
        )
    }
}

@Composable
fun Email(email: String) {
    if (email.isNotEmpty()) {
        Text(
            text = email,
            style = MaterialTheme.typography.bodyMedium,
            color = colorResource(R.color.black_50_percent),
            fontSize = dimensionResource(id = R.dimen.info_text_size).value.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(R.dimen.margin_medium))
                .wrapContentWidth(Alignment.CenterHorizontally),
        )
    }
}

@Composable
fun ClickableLinkText(url: String) {
    if (url.isNotEmpty()) {
        val annotatedString = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = colorResource(id = R.color.blue),
                    textDecoration = TextDecoration.Underline
                )
            ) {
                append(url)
                addStringAnnotation("URL", url, 0, length)
            }
        }

        val context = LocalContext.current

        Text(
            text = annotatedString,
            fontSize = dimensionResource(id = R.dimen.info_text_size).value.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(R.dimen.margin_medium))
                .clickable {
                    openBrowser(context, url)
                }
                .wrapContentWidth(Alignment.CenterHorizontally)
        )
    }
}
