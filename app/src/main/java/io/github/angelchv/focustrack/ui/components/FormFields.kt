package io.github.angelchv.focustrack.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import io.github.angelchv.focustrack.R

@Composable
fun UsernameField(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        maxLines = 1,
        label = { Text(stringResource(R.string.username_label)) }
    )
}

@Composable
fun EmailField(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        maxLines = 1,
        label = { Text(stringResource(R.string.email_label)) },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
    )
}

@Composable
fun PasswordField(
    value: String, isHidingPassword: Boolean,
    onToggleHidePassword: () -> Unit,
    onValueChange: (String) -> Unit,
) {
    val visualTransformation = if (isHidingPassword) PasswordVisualTransformation()
    else VisualTransformation.None

    @Composable
    fun leadingIcon() = if (isHidingPassword) {
        IconButton(onToggleHidePassword) {
            Icon(
                painter = painterResource(R.drawable.eye_24),
                stringResource(R.string.show_password)
            )
        }
    } else {
        IconButton(onToggleHidePassword) {
            Icon(
                painter = painterResource(R.drawable.hide_eye_black_24),
                stringResource(R.string.hide_password)
            )
        }
    }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        maxLines = 1,
        label = { Text(stringResource(R.string.password_label)) },
        trailingIcon = { leadingIcon() },
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    )
}
