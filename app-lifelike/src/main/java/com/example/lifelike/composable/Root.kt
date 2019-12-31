package com.example.lifelike.composable

import androidx.compose.Composable
import com.example.lifelike.composable.LoggedIn.Routing.Gallery
import com.example.lifelike.composable.LoggedOut.Routing.Splash
import com.example.lifelike.entity.User
import com.github.zsoltk.backtrack.composable.BackHandler
import java.io.Serializable

interface Root {

    sealed class Routing : Serializable {
        object LoggedOut: Routing()
        data class LoggedIn(val user: User): Routing()
    }

    companion object {
        @Composable
        fun Content(defaultRouting: Routing) {
            BackHandler("Root", defaultRouting) { backStack ->
                when (val currentRouting = backStack.last()) {
                    is Routing.LoggedOut -> LoggedOut.Content(
                        defaultRouting = Splash,
                        onLoggedIn = { user ->
                            // play around with other back stack operations here:
                            backStack.newRoot(
                                Routing.LoggedIn(user)
                            )
                        }
                    )
                    is Routing.LoggedIn -> LoggedIn.Content(
                        defaultRouting = Gallery,
                        user = currentRouting.user,
                        onLogout = {
                            // play around with other back stack operations here:
                            backStack.newRoot(
                                Routing.LoggedOut
                            )
                        }
                    )
                }
            }
        }
    }
}
