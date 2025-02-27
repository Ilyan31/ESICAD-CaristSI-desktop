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
import ui.LoginScreen
import ui.MainMenuScreen

@Composable
@Preview
fun App(database: Database) {
    val router = remember { Router() }
    var isAuthenticated by remember { mutableStateOf(false) } // ✅ Correction

    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            if (!isAuthenticated) {
                LoginScreen { email, password ->
                    println("Tentative de connexion avec $email")

                    val userExists = database.from(Caristes)
                        .select()
                        .where { (Caristes.login eq email) and (Caristes.mdp eq password) }
                        .totalRecords > 0 // ✅ Correction

                    if (userExists) {
                        isAuthenticated = true
                        router.navigateTo(Routes.HOME) // Naviguer vers le menu principal
                    }
                }
            } else {
                MainMenuScreen(router) // Afficher directement le menu principal
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