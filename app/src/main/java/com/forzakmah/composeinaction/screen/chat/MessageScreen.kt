package com.forzakmah.composeinaction.screen.chat

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.forzakmah.composeinaction.R
import kotlinx.coroutines.launch

@Composable
fun MessageScreen(
    onBackClick: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    Scaffold(
        topBar = {
            ChatAppBar(
                title = stringResource(id = R.string.title_for_chat_app_bar),
                subTitle = stringResource(id = R.string.app_name),
                onBackClick = onBackClick
            ) {
                scope.launch {
                    Toast.makeText(
                        context,
                        "MenuItem ${it.name} with id =${it.index} clicked", Toast.LENGTH_LONG
                    )
                        .show()
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize(1f)
                .padding(paddingValues),
            contentAlignment = Alignment.BottomCenter
        ) {
            CustomClickableText()
        }
    }
}

@Composable
fun CustomClickableText() {
    val firstLink = "Privacy policy"
    val secondLink = "Terms of Service"
    val str = "Read our $firstLink. Tap 'Agree and continue' to accept $secondLink"

    val startIndexOfFirstLink = str.indexOf(firstLink)
    val endIndexOfFirstLink = startIndexOfFirstLink + firstLink.length

    val startIndexOfSecondLink = str.indexOf(secondLink)
    val endIndexOfSecondLink = startIndexOfSecondLink + secondLink.length

    val annotatedString = buildAnnotatedString {
        append(str)
        addStringAnnotation(
            tag = "policy",
            annotation = "https://www.whatsapp.com/legal/privacy-policy",
            start = startIndexOfFirstLink,
            end = endIndexOfFirstLink
        )
        addStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary
            ),
            start = startIndexOfFirstLink,
            end = endIndexOfFirstLink
        )
        addStringAnnotation(
            tag = "terms",
            annotation = "https://www.whatsapp.com/legal/terms-of-service",
            start = startIndexOfSecondLink,
            end = endIndexOfSecondLink
        )
        addStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary
            ),
            start = startIndexOfSecondLink,
            end = endIndexOfSecondLink
        )
    }
    ClickableText(
        modifier = Modifier.padding(horizontal = 16.dp),
        text = annotatedString,
        style = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.Center),
        onClick = { offset ->
            val policy = annotatedString.getStringAnnotations(
                tag = "policy",
                start = offset,
                end = offset
            )
            if (policy.isNotEmpty()) {
                /**
                 * open URI in a browser.
                 */
                Log.e("policy url", policy.first().item)
            }

            val terms = annotatedString.getStringAnnotations(
                tag = "terms",
                start = offset,
                end = offset
            )
            if (terms.isNotEmpty()) {
                /**
                 * open browser
                 */
                Log.e("terms url", terms.first().item)
            }
        }
    )
}