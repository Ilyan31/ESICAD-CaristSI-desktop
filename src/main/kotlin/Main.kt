import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import database.DB
import ktorm.Caristes
import org.ktorm.database.Database
import org.ktorm.dsl.*
import routing.Router
import routing.Routes
import ui.*

@Composable
@Preview
fun App(router: Router) {
    var isAuthenticated by remember { mutableStateOf(false) }

    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            when {
                !isAuthenticated -> LoginScreen { email, password ->
                    println("🔐 Tentative de connexion avec $email")

                    val userExists = DB.database.from(Caristes)
                        .select()
                        .where { (Caristes.login eq email) and (Caristes.mdp eq password) }
                        .totalRecords > 0

                    if (userExists) {
                        println("✅ Connexion réussie")
                        isAuthenticated = true
                        router.navigateTo(Routes.HOME)
                    } else {
                        println("❌ Échec de connexion")
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
    // 🔹 Connexion à la base de données
    val database = Database.connect(
        url = "jdbc:mysql://localhost:3306/carist-si",
        user = "root",
        password = "",
        driver = "com.mysql.cj.jdbc.Driver"
    )

    // 🔹 Vérification des colis existants
    val nombreColis = database.useConnection { conn ->
        conn.prepareStatement("SELECT COUNT(*) FROM Colis").executeQuery().use { rs ->
            if (rs.next()) rs.getInt(1) else 0
        }
    }

    println("📦 Nombre de colis en base : $nombreColis")

    Window(onCloseRequest = ::exitApplication) {
        App(router = remember { Router() }) // ✅ Correction de l’appel de `App`
    }
}