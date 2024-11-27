/*
 * MIT License
 *
 * Copyright (c) 2024 AnyGogin31
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package neilt.mobile.pixiv.ui.components.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class BadgeStyle(
    val backgroundColor: Color = Color.Red,
    val textColor: Color = Color.White,
    val textStyle: TextStyle = TextStyle.Default,
)

@Stable
data class Badge(
    val count: Int,
    val style: BadgeStyle = BadgeStyle(),
)

@Stable
data class NavigationItemContent(
    val label: @Composable () -> String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badge: Badge? = null,
    val customSlot: (@Composable () -> Unit)? = null,
    val isEnabled: Boolean = true,
)

@Stable
data class BottomNavigationItem(
    val route: String,
    val content: NavigationItemContent,
    val onSelect: () -> Unit = {},
    val onReselect: () -> Unit = {},
)

@Composable
fun BottomNavigationBar(
    items: List<BottomNavigationItem>,
    currentRoute: String,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = MaterialTheme.colorScheme.primary,
    tonalElevation: Dp = 0.dp,
) {
    BottomAppBar(
        modifier = modifier.fillMaxWidth(),
        containerColor = containerColor,
        contentColor = contentColor,
        tonalElevation = tonalElevation,
    ) {
        items.forEach { item ->
            val isSelected = item.route == currentRoute

            NavigationBarItem(
                icon = {
                    NavigationIcon(item, isSelected)
                },
                label = {
                    Text(item.content.label())
                },
                selected = isSelected,
                enabled = item.content.isEnabled,
                onClick = {
                    if (isSelected) item.onReselect() else item.onSelect()
                },
            )
        }
    }
}

@Composable
private fun NavigationIcon(item: BottomNavigationItem, isSelected: Boolean) {
    item.content.customSlot?.invoke() ?: BadgedBox(
        badge = {
            item.content.badge
                ?.takeIf { it.count > 0 }
                ?.let { BadgeView(it) }
        },
    ) {
        Icon(
            imageVector = if (isSelected) item.content.selectedIcon else item.content.unselectedIcon,
            contentDescription = "${if (isSelected) "Selected" else "Unselected"} ${item.route}",
        )
    }
}

@Composable
private fun BadgeView(badge: Badge) {
    Box(
        modifier = Modifier
            .background(badge.style.backgroundColor, shape = CircleShape)
            .padding(4.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = if (badge.count > 99) "99+" else badge.count.toString(),
            color = badge.style.textColor,
            style = badge.style.textStyle,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    MaterialTheme {
        val items = listOf(
            BottomNavigationItem(
                route = "home",
                content = NavigationItemContent(
                    label = { "Home" },
                    selectedIcon = Icons.Filled.Home,
                    unselectedIcon = Icons.Outlined.Home,
                    badge = Badge(count = 9),
                ),
            ),
            BottomNavigationItem(
                route = "search",
                content = NavigationItemContent(
                    label = { "Search" },
                    selectedIcon = Icons.Default.Search,
                    unselectedIcon = Icons.Default.Search,
                ),
            ),
            BottomNavigationItem(
                route = "profile",
                content = NavigationItemContent(
                    label = { "Profile" },
                    selectedIcon = Icons.Filled.Person,
                    unselectedIcon = Icons.Outlined.Person,
                    badge = Badge(count = 102),
                ),
            ),
        )

        BottomNavigationBar(
            items = items,
            currentRoute = "home",
        )
    }
}
