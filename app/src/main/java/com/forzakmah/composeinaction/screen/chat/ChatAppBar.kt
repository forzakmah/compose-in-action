package com.forzakmah.composeinaction.screen.chat

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.rounded.Block
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Report
import androidx.compose.material.icons.rounded.Videocam
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object ChatAppbarProperties {
    const val backgroundColor = 0xff075E54
    const val height = 64.0
}

@Composable
fun ChatAppBar(
    title: String,
    subTitle: String? = null,
    onBackClick: (() -> Unit)? = null,
    onMenuItemClick: ((MenuItem) -> Unit)? = null
) {
    val menuItems = listOf(
        MenuItem(
            index = 0,
            name = "Call",
            icon = Icons.Rounded.Call,
            contentDescription = null,
            enabled = true,
            showAsAction = MenuItemMode.ALWAYS
        ),
        MenuItem(
            index = 1,
            name = "Camera",
            icon = Icons.Rounded.Videocam,
            contentDescription = null,
            enabled = true,
            showAsAction = MenuItemMode.IF_ROOM
        ),
        MenuItem(
            index = 3,
            name = "Delete",
            icon = Icons.Rounded.Delete,
            contentDescription = null,
            enabled = true,
            showAsAction = MenuItemMode.IF_ROOM,
        ),
        MenuItem(
            index = 4,
            name = "Report",
            icon = Icons.Rounded.Report,
            contentDescription = null,
            enabled = true,
            showAsAction = MenuItemMode.IF_ROOM
        ),
        MenuItem(
            index = 5,
            name = "Block",
            icon = Icons.Rounded.Block,
            contentDescription = null,
            enabled = true,
            showAsAction = MenuItemMode.NEVER
        )
    )
    Surface(
        shadowElevation = 4.dp,
        contentColor = Color.White,
        color = Color(ChatAppbarProperties.backgroundColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(ChatAppbarProperties.height.dp)
        ) {
            Row(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    modifier = Modifier
                        .clickable {
                            onBackClick?.invoke()
                        }
                        .padding(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )

                    Surface(
                        modifier = Modifier.padding(6.dp),
                        shape = CircleShape,
                        color = Color.LightGray
                    ) {
                        Icon(
                            modifier = Modifier
                                .padding(4.dp)
                                .background(Color.LightGray)
                                .fillMaxHeight()
                                .aspectRatio(1f),
                            imageVector = Icons.Filled.People,
                            contentDescription = "Account"
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .padding(2.dp)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = title,
                        fontSize = 16.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    subTitle?.let {
                        Text(
                            subTitle,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            AppBarRightMenu(
                menuItems = menuItems,
                onMenuItemClick = onMenuItemClick,
                defaultIconNumber = 3
            )
        }
    }
}

@Composable
fun AppBarRightMenu(
    modifier: Modifier = Modifier,
    menuItems: List<MenuItem>,
    defaultIconNumber: Int = 3,
    onMenuItemClick: ((MenuItem) -> Unit)? = null
) {
    val (actionMenuItems, overflowMenuItems) = splitMenuItems(
        items = menuItems,
        defaultIconNumber = defaultIconNumber
    )

    Row(
        modifier = modifier.fillMaxHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        /**
         * Display always menuItem
         */
        actionMenuItems.forEach { menuItem ->
            IconButton(
                enabled = menuItem.enabled,
                onClick = {
                    onMenuItemClick?.invoke(menuItem)
                }
            ) {
                Icon(
                    imageVector = menuItem.icon,
                    contentDescription = menuItem.contentDescription,
                    tint = Color.White
                )
            }
        }

        /**
         * Display overflow Menu Items if exists
         */
        if (overflowMenuItems.isNotEmpty()) {
            Box {
                var expand by remember { mutableStateOf(false) }
                IconButton(onClick = { expand = !expand }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "moreVertical")
                }
                DropdownMenu(
                    expanded = expand,
                    onDismissRequest = { expand = false }
                ) {
                    overflowMenuItems.forEach { menuItem ->
                        DropdownMenuItem(
                            text = {
                                Text(menuItem.name)
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = menuItem.icon,
                                    contentDescription = menuItem.contentDescription
                                )
                            },
                            onClick = {
                                expand = !expand
                                onMenuItemClick?.invoke(menuItem)
                            },
                            enabled = menuItem.enabled
                        )
                    }
                }
            }
        }
    }
}

data class MenuItem(
    val index: Int,
    val name: String,
    val icon: ImageVector,
    val contentDescription: String? = null,
    val enabled: Boolean,
    val showAsAction: MenuItemMode = MenuItemMode.IF_ROOM,
)

enum class MenuItemMode {
    ALWAYS,
    NEVER,
    IF_ROOM
}

fun splitMenuItems(
    items: List<MenuItem>,
    defaultIconNumber: Int
): Pair<List<MenuItem>, List<MenuItem>> {
    var (alwaysCount, neverCount, ifRoomCount) = Triple(first = 0, second = 0, third = 0)
    items.forEach { menuItem ->
        when (menuItem.showAsAction) {
            MenuItemMode.ALWAYS -> alwaysCount++
            MenuItemMode.NEVER -> neverCount++
            MenuItemMode.IF_ROOM -> ifRoomCount++
        }
    }
    val needOverflowMenu = alwaysCount + ifRoomCount > defaultIconNumber || neverCount > 0

    /**
     * if we need to display overflow menu we must add a icon [More Vertical]
     */
    val iconNumber = defaultIconNumber - (if (needOverflowMenu) 1 else 0)

    var totalIfRoomToDisplay = iconNumber - alwaysCount

    val actionItems = mutableListOf<MenuItem>()
    val overflowItems = mutableListOf<MenuItem>()
    for (menuItem in items) {
        when (menuItem.showAsAction) {
            MenuItemMode.ALWAYS -> actionItems.add(menuItem)
            MenuItemMode.NEVER -> overflowItems.add(menuItem)
            MenuItemMode.IF_ROOM -> {
                if (totalIfRoomToDisplay > 0) {
                    actionItems.add(menuItem)
                    totalIfRoomToDisplay--
                } else
                    overflowItems.add(menuItem)
            }
        }
    }

    /**
     * send pair of list
     */
    return Pair(
        actionItems.toList(),
        overflowItems.toList()
    )
}

@Preview
@Preview("dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(device = Devices.PIXEL_4_XL)
@Preview(device = Devices.PIXEL)
@Composable
fun PreviewChatAppBar() {
    ChatAppBar(
        title = "Android group",
        subTitle = "Compose in action"
    ) {

    }
}