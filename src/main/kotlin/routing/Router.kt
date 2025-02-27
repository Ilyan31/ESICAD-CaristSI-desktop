package routing

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

enum class Routes(val route: String) {
    LOGIN("LOGIN"),
    HOME("HOME"),
    GESTION_CARISTES("GESTION_CARISTES"),
    GESTION_COLIS("GESTION_COLIS"),
    GESTION_EMPLACEMENTS("GESTION_EMPLACEMENTS")
}

class Router {
    var currentRoute by mutableStateOf<Routes>(Routes.LOGIN)
        private set

    fun navigateTo(route: Routes) {
        currentRoute = route
    }
}