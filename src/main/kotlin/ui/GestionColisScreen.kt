package ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import repositories.ColisRepository
import routing.Router
import routing.Routes

@Composable
fun GestionColisScreen(router: Router) {
    val colisList = remember { mutableStateListOf<Map<String, Any>>() }
    var isLoading by remember { mutableStateOf(true) }

    // Récupérer les colis dès l'affichage de l'écran
    LaunchedEffect(Unit) {
        val colis = ColisRepository.getAllColis()
        colisList.clear()
        colisList.addAll(colis)
        isLoading = false
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Gestion des Colis", style = MaterialTheme.typography.h4, modifier = Modifier.padding(16.dp))

        if (isLoading) {
            // 🔹 Indicateur de chargement
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        } else if (colisList.isEmpty()) {
            // 🔹 Message si aucun colis trouvé
            Text("Aucun colis trouvé dans la base de données.", color = MaterialTheme.colors.error)
        } else {
            // 🔹 Liste des colis
            LazyColumn {
                items(colisList) { colis ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        elevation = 4.dp
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("📦 ID: ${colis["id"]}")
                            Text("🔢 Code: ${colis["code"]}")
                            Text("📝 Description: ${colis["description"]}")
                            Text("⚖️ Poids: ${colis["poids"]} kg")
                            Text("📏 Volume: ${colis["volume"]} m³")
                            Text("📅 Réception: ${colis["dateReception"]}")
                            Text("📍 ID Emplacement: ${colis["idEmplacement"]}")
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Bouton de retour au menu principal
        Button(
            onClick = { router.navigateTo(Routes.HOME) },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Text("Retour au menu principal")
        }
    }
}