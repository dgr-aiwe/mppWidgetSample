package org.example.mpp.auth

import dev.icerock.moko.resources.desc.desc
import dev.icerock.moko.widgets.ButtonWidget
import dev.icerock.moko.widgets.button
import dev.icerock.moko.widgets.constraint
import dev.icerock.moko.widgets.core.Theme
import dev.icerock.moko.widgets.core.Value
import dev.icerock.moko.widgets.screen.Args
import dev.icerock.moko.widgets.screen.WidgetScreen
import dev.icerock.moko.widgets.screen.navigation.*
import dev.icerock.moko.widgets.style.view.WidgetSize

class ProfileScreen(
    private val theme: Theme,
    private val routeEdit: RouteWithResult<Unit, Boolean>,
    private val routeLogout: Route<Unit>,
    private val routeFriends: Route<Unit>
) : WidgetScreen<Args.Empty>(), NavigationItem {

    override val navigationBar: NavigationBar get() = NavigationBar.Normal("Profile".desc())

    private val editHandler by registerRouteHandler(code = 9, route = routeEdit) {
        println("profile edited: $it")
    }

    override fun createContentWidget() = with(theme) {
        constraint(size = WidgetSize.AsParent) {
            val editButton = +button(
                size = WidgetSize.WidthAsParentHeightWrapContent,
                content = ButtonWidget.Content.Text(Value.data("Edit".desc()))
            ) {
                routeEdit.route(this@ProfileScreen, editHandler)
            }

            val logoutButton = +button(
                size = WidgetSize.WidthAsParentHeightWrapContent,
                content = ButtonWidget.Content.Text(Value.data("Logout".desc()))
            ) {
                routeLogout.route()
            }

            val friendsButton = +button(
                size = WidgetSize.WidthAsParentHeightWrapContent,
                content = ButtonWidget.Content.Text(Value.data("Friends".desc()))
            ) {
                routeFriends.route()
            }

            constraints {
                editButton centerYToCenterY root
                editButton centerXToCenterX root

                logoutButton centerXToCenterX root
                logoutButton topToBottom editButton

                friendsButton topToBottom logoutButton
                friendsButton centerXToCenterX root
            }
        }
    }
}
