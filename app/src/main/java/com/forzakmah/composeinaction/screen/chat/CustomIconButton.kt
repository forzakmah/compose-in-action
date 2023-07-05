package com.forzakmah.composeinaction.screen.chat

import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

@Composable
fun CustomIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enable: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    indication: Indication = rememberRipple(bounded = false, radius = 24.dp),
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier.clickable(
            onClick = onClick,
            enabled = enable,
            role = Role.Button,
            indication = indication,
            interactionSource = interactionSource
        ),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}