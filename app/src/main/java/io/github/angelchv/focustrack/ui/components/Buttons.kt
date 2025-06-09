package io.github.angelchv.focustrack.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.angelchv.focustrack.R
import io.github.angelchv.focustrack.ui.theme.robotoMediumFontFamily

@Composable
fun EnableButton(text: String, enabled: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        enabled = enabled,
    ) {
        Text(text)
    }
}

@Composable
fun GoogleSignInButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val isDarkTheme = isSystemInDarkTheme()

    val backgroundColor = if (isDarkTheme) Color(0xFF131314) else Color(0xFFFFFFFF)
    val borderColor = if (isDarkTheme) Color(0xFF8E918F) else Color(0xFF747775)
    val textColor = if (isDarkTheme) Color(0xFFE3E3E3) else Color(0xFF1F1F1F)

    Surface(
        modifier = modifier
            .height(40.dp)
            .wrapContentWidth()
            .clip(RoundedCornerShape(50))
            .border(1.dp, borderColor, RoundedCornerShape(50))
            .clickable { onClick() },
        color = backgroundColor
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 12.dp, end = 12.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.google_g_logo),
                contentDescription = "Google logo",
                tint = Color.Unspecified,
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = stringResource(R.string.google_sign_in),
                color = textColor,
                style = TextStyle(fontFamily = robotoMediumFontFamily)
            )
        }
    }
}


@Preview(
    name = "Light Button", group = "GoogleSignInButton",
)
@Composable
fun GoogleSignInButtonLightPreview() {
    GoogleSignInButton(onClick = {})
}

@Preview(
    name = "Dark Button", group = "GoogleSignInButton",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun GoogleSignInButtonDarkPreview() {
    GoogleSignInButton(onClick = {})
}