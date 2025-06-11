package io.github.angelchv.focustrack.ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.angelchv.focustrack.domain.model.User
import io.github.angelchv.focustrack.ui.components.UserImage
import io.github.angelchv.focustrack.ui.theme.FocusTrackTheme

@Composable
fun ProfileHeader(user: User?, modifier: Modifier = Modifier) {
    Surface(
        modifier, color = MaterialTheme.colorScheme.primaryContainer,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            UserImage(photoUrl = user?.photoUrl, modifier = Modifier.size(128.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = user?.name ?: "UserName",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = user?.email ?: "Email",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileHeaderPreview() {
    FocusTrackTheme {
        ProfileHeader(User.example)
    }
}