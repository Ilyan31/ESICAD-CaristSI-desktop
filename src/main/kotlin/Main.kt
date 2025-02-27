import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ktorm.Caristes
import org.ktorm.database.Database
import org.ktorm.dsl.*
import routing.Router
import routing.Routes
import ui.*

@Composable
@Preview
fun App(database: Database) {
    val router = remember { Router() }
    var isAuthenticated by remember { mutableStateOf(false) }

    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            when {
                !isAuthenticated -> LoginScreen { email, password ->
                    println("Tentative de connexion avec $email")

                    val userExists = database.from(Caristes)
                        .select()
                        .where { (Caristes.login eq email) and (Caristes.mdp eq password) }
                        .totalRecords > 0

                    if (userExists) {
                        isAuthenticated = true
                        router.navigateTo(Routes.HOME)
                    }
                }

                router.currentRoute == Routes.HOME -> MainMenuScreen(router, onLogout = { isAuthenticated = false })
                router.currentRoute == Routes.GESTION_CARISTES -> GestionCaristesScreen(router)
                router.currentRoute == Routes.GESTION_COLIS -> GestionColisScreen(router)
                router.currentRoute == Routes.GESTION_EMPLACEMENTS -> GestionEmplacementsScreen(router)
            }
        }
    }
}

fun main() = application {
    val database = Database.connect(
        url = "jdbc:mysql://localhost:3306/carist-si",
        user = "root",
        password = null
    )

    Window(onCloseRequest = ::exitApplication) {
        App(database)
    }
}